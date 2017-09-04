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
 *            we want to execute this type of Entity implementation
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
     * @param id id
     * @return entity with id
     * @throws DAOException if SQLException occurred
     */
    T findEntityById(long id) throws DAOException;

    /**
     * Deletes entity with id from database.
     *
     * @param id id
     * @return <code>true</code> if entity with id was deleted successfully;
     * <code>false</code> otherwise
     * @throws DAOException                if SQLException occurred
     * @throws MethodNotSupportedException if delete operation not supported
     */
    boolean delete(long id) throws DAOException, MethodNotSupportedException;

    /**
     * Creates entity.
     *
     * @param entity entity
     * @return <code>true</code> if entity was created successfully;
     * <code>false</code> otherwise
     * @throws DAOException                if SQLException occurred
     * @throws MethodNotSupportedException if create operation not supported
     */
    boolean create(T entity) throws DAOException, MethodNotSupportedException;

    /**
     * Updates entity.
     *
     * @param entity entity
     * @return <code>true</code> if entity was updated successfully;
     * <code>false</code> otherwise
     * @throws DAOException                if SQLException occurred
     * @throws MethodNotSupportedException if update operation not supported
     */
    boolean update(T entity) throws DAOException, MethodNotSupportedException;

}