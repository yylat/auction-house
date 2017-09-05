package com.epam.auction.dao;

import com.epam.auction.entity.Entity;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.db.ProxyConnection;

import java.util.List;

/**
 * Provides the base model DAO interface.
 *
 * @param <T> indicates that for this instantiation of the DAO,
 *            will be used this type of Entity implementation
 */
public interface GenericDAO<T extends Entity> {

    /**
     * Sets connection to DAO.
     *
     * @param connection connection
     */
    void setConnection(ProxyConnection connection);

    /**
     * Returns all entities.
     *
     * @return all entities
     * @throws DAOException if SQLException occurred
     */
    List<T> findAll() throws DAOException;

    /**
     * Returns entity with id.
     *
     * @param id entity id
     * @return entity with id
     * @throws DAOException if SQLException occurred
     */
    T findEntityById(long id) throws DAOException;

    /**
     * Deletes entity with id from database.
     *
     * @param id entity id
     * @throws DAOException                if SQLException occurred
     * @throws MethodNotSupportedException if delete operation not supported
     */
    void delete(long id) throws DAOException, MethodNotSupportedException;

    /**
     * Inserts entity into database.
     *
     * @param entity entity to insert
     * @throws DAOException                if SQLException occurred
     * @throws MethodNotSupportedException if create operation not supported
     */
    void create(T entity) throws DAOException, MethodNotSupportedException;

    /**
     * Updates entity.
     *
     * @param entity entity to update
     * @throws DAOException                if SQLException occurred
     * @throws MethodNotSupportedException if update operation not supported
     */
    void update(T entity) throws DAOException, MethodNotSupportedException;

}