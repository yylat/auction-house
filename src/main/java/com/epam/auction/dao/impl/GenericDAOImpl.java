package com.epam.auction.dao.impl;

import com.epam.auction.dao.GenericDAO;
import com.epam.auction.entity.Entity;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.db.ProxyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAOImpl<T extends Entity> implements GenericDAO<T> {

    ProxyConnection connection;

    private final String queryFindAll;
    private final String queryFindById;
    private final String queryDelete;
    private final String queryCreate;
    private final String queryUpdate;

    GenericDAOImpl(String queryFindAll, String queryFindById, String queryDelete, String queryCreate, String queryUpdate) {
        this.queryFindAll = queryFindAll;
        this.queryFindById = queryFindById;
        this.queryDelete = queryDelete;
        this.queryCreate = queryCreate;
        this.queryUpdate = queryUpdate;
    }

    public void setConnection(ProxyConnection connection) {
        this.connection = connection;
    }

    public List<T> findAll() throws DAOLayerException {
        List<T> entities;

        try (PreparedStatement statement = connection.prepareStatement(queryFindAll)) {
            ResultSet resultSet = statement.executeQuery();
            entities = new ArrayList<>();
            while (resultSet.next()) {
                entities.add(extractEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }

        return entities;
    }

    public T findEntityById(int id) throws DAOLayerException {
        T entity = null;

        try (PreparedStatement statement = connection.prepareStatement(queryFindById)) {
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                entity = extractEntity(resultSet);
            }

        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }

        return entity;
    }

    public boolean delete(int id) throws DAOLayerException, MethodNotSupportedException {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(queryDelete)) {
            statement.setInt(1, id);
            if (statement.executeUpdate() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }

        return result;
    }

    public boolean delete(T entity) throws DAOLayerException, MethodNotSupportedException {
        return delete(entity.getId());
    }

    public boolean create(T entity) throws DAOLayerException {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(queryCreate, Statement.RETURN_GENERATED_KEYS)) {
            defineQueryAttributes(entity, statement);
            if (statement.executeUpdate() != 0) {
                ResultSet keys = statement.getGeneratedKeys();
                if (keys.next()) {
                    entity.setId(keys.getInt(1));
                    result = true;
                }
            }
        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }

        return result;
    }

    public boolean update(T entity) throws DAOLayerException, MethodNotSupportedException {
        boolean result = false;

        try (PreparedStatement statement = connection.prepareStatement(queryUpdate)) {
            defineQueryAttributes(entity, statement);
            statement.setInt(entity.getFieldsNumber(), entity.getId());
            if (statement.executeUpdate() != 0) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }

        return result;
    }

    abstract T extractEntity(ResultSet resultSet) throws SQLException;

    abstract void defineQueryAttributes(T entity, PreparedStatement statement) throws SQLException;

}