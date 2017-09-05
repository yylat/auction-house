package com.epam.auction.dao.impl;

import com.epam.auction.dao.GenericDAO;
import com.epam.auction.dao.functional.StatementBiConsumer;
import com.epam.auction.dao.functional.StatementConsumer;
import com.epam.auction.db.ProxyConnection;
import com.epam.auction.entity.Entity;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides the base model implementation for DAO.
 *
 * @param <T> indicates that for this instantiation of the DAO,
 *            will be used this type of Entity implementation
 */
public abstract class GenericDAOImpl<T extends Entity> implements GenericDAO<T> {

    /**
     * Proxy connection.
     */
    ProxyConnection connection;

    /**
     * Query for select all entities from table.
     */
    private final String queryFindAll;
    /**
     * Query for find entity from table by id.
     */
    private final String queryFindById;
    /**
     * Query to delete entity from table.
     */
    private final String queryDelete;
    /**
     * Query to insert entity into table.
     */
    private final String queryCreate;
    /**
     * Query to update entity.
     */
    private final String queryUpdate;

    /**
     * Constructs dao with basic queries.
     *
     * @param queryFindAll
     * @param queryFindById
     * @param queryDelete
     * @param queryCreate
     * @param queryUpdate
     */
    GenericDAOImpl(String queryFindAll, String queryFindById, String queryDelete, String queryCreate, String queryUpdate) {
        this.queryFindAll = queryFindAll;
        this.queryFindById = queryFindById;
        this.queryDelete = queryDelete;
        this.queryCreate = queryCreate;
        this.queryUpdate = queryUpdate;
    }

    @Override
    public void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }

    @Override
    public List<T> findAll() throws DAOException {
        return findList(queryFindAll);
    }

    @Override
    public T findEntityById(long id) throws DAOException {
        return findEntity(queryFindById, statement -> statement.setLong(1, id));
    }

    @Override
    public void delete(long id) throws DAOException, MethodNotSupportedException {
        executeUpdate(queryDelete, statement -> statement.setLong(1, id));
    }

    @Override
    public void create(T entity) throws DAOException, MethodNotSupportedException {
        executeCreate(queryCreate, entity, this::defineQueryAttributes);
    }

    @Override
    public void update(T entity) throws DAOException, MethodNotSupportedException {
        executeUpdate(queryUpdate, entity, (en, st) -> {
            defineQueryAttributes(en, st);
            st.setLong(en.getFieldsNumber(), en.getId());
        });
    }

    /**
     * Extract entity from the result set.
     *
     * @param resultSet result set from statement
     * @return entity from the result set
     * @throws SQLException if can not extract entity
     */
    abstract T extractEntity(ResultSet resultSet) throws SQLException;

    /**
     * Sets entity data to prepared statement.
     *
     * @param entity    entity to execute query on it
     * @param statement prepared statement
     * @throws SQLException if can not set entity data to prepared statement
     */
    abstract void defineQueryAttributes(T entity, PreparedStatement statement) throws SQLException;

    /**
     * Returns a range of the entities according to the query.
     *
     * @param query             query to execute
     * @param statementConsumer method of setting data to prepared statement
     * @return the range of the entities according to query
     * @throws DAOException if SQL exception occurred
     */
    List<T> findSpecificList(String query, StatementConsumer statementConsumer) throws DAOException {
        List<T> entities;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statementConsumer.accept(statement);
            ResultSet resultSet = statement.executeQuery();
            entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(extractEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return entities;
    }

    /**
     * Returns all entities according to the query.
     *
     * @param query query to execute
     * @return all entities according to the query
     * @throws DAOException if SQL exception occurred
     */
    List<T> findList(String query) throws DAOException {
        List<T> entities;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(extractEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return entities;
    }

    /**
     * Returns entity according to the query.
     *
     * @param query             query to execute
     * @param statementConsumer method of setting data to prepared statement
     * @return entity according to the query
     * @throws DAOException if SQL exception occurred
     */
    T findEntity(String query, StatementConsumer statementConsumer) throws DAOException {
        T entity = null;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statementConsumer.accept(statement);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = extractEntity(resultSet);
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return entity;
    }

    /**
     * Executes update according to the query.
     *
     * @param query             query to execute
     * @param statementConsumer method of setting data to prepared statement
     * @throws DAOException if SQL exception occurred
     */
    void executeUpdate(String query, StatementConsumer statementConsumer) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statementConsumer.accept(statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Executes entity update according to the query.
     *
     * @param query               query to execute
     * @param entity              entity to execute query on it
     * @param statementBiConsumer method of setting data to prepared statement from entity
     * @throws DAOException if SQL exception occurred
     */
    void executeUpdate(String query, T entity, StatementBiConsumer<T> statementBiConsumer) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statementBiConsumer.accept(entity, statement);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Executes entity insert according to the query.
     *
     * @param query               query to execute
     * @param entity              entity to execute query on it
     * @param statementBiConsumer method of setting data to prepared statement from entity
     * @throws DAOException if SQL exception occurred
     */
    void executeCreate(String query, T entity, StatementBiConsumer<T> statementBiConsumer) throws DAOException {
        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statementBiConsumer.accept(entity, statement);
            if (statement.executeUpdate() != 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    entity.setId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    /**
     * Returns records number according to the query.
     *
     * @param query             query to execute
     * @param statementConsumer method of setting data to prepared statement
     * @return records number
     * @throws DAOException if SQL exception occurred
     */
    int countRows(String query, StatementConsumer statementConsumer) throws DAOException {
        int rows = 0;

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statementConsumer.accept(statement);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                rows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }

        return rows;
    }

}