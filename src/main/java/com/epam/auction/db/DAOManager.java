package com.epam.auction.db;

import com.epam.auction.dao.GenericDAO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

/**
 * Provides service to manage connections and transactions for DAO classes.
 * For non transactional operations can use try-with-resources.
 */
public class DAOManager implements AutoCloseable {

    /**
     * Logger to write logs.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Connection from ConnectionPool.
     */
    private ProxyConnection connection = ConnectionPool.getInstance().takeConnection();

    /**
     * Shows if there is necessity of transactions.
     * Default value - false.
     */
    private boolean isTransactional = false;

    /**
     * Constructs DAOManager with at least one dao.
     *
     * @param dao       dao
     * @param otherDAOs other dao (optional)
     */
    public DAOManager(GenericDAO dao, GenericDAO... otherDAOs) {
        setConnection(dao, otherDAOs);
    }

    /**
     * Constructs DAOManager with transactional status and at least one dao.
     *
     * @param isTransactional transactional status
     * @param dao             dao
     * @param otherDAOs       other dao (optional)
     */
    public DAOManager(boolean isTransactional, GenericDAO dao, GenericDAO... otherDAOs) {
        this.isTransactional = isTransactional;
        setConnection(dao, otherDAOs);
    }

    /**
     * Begins transaction.
     */
    public void beginTransaction() {
        if (isTransactional) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Exception while trying to begin transaction.", e);
            }
        }
    }

    /**
     * Ends transaction and return connection to ConnectionPool.
     */
    public void endTransaction() {
        if (isTransactional) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, "Exception while trying to end transaction.", e);
            }
        }
        ConnectionPool.getInstance().returnConnection(connection);
    }

    /**
     * Commits transaction.
     */
    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Commit error.", e);
        }
    }

    /**
     * Roolbacks transaction.
     */
    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, "Rollback error.", e);
        }
    }

    /**
     * Returns connection to ConnectionPool.
     */
    @Override
    public void close() {
        ConnectionPool.getInstance().returnConnection(connection);
    }

    /**
     * Sets connection to all dao.
     *
     * @param dao       dao
     * @param otherDAOs other dao (optional)
     * @see GenericDAO#setConnection(ProxyConnection)
     */
    private void setConnection(GenericDAO dao, GenericDAO[] otherDAOs) {
        dao.setConnection(connection);
        for (GenericDAO anotherDAO : otherDAOs) {
            anotherDAO.setConnection(connection);
        }
    }

}