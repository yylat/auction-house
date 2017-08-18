package com.epam.auction.db;

import com.epam.auction.dao.GenericDAO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;

public class DAOManager implements AutoCloseable {

    private final static Logger logger = LogManager.getLogger();

    private ProxyConnection connection = ConnectionPool.getInstance().takeConnection();

    private boolean isTransactional = false;

    public DAOManager(GenericDAO dao, GenericDAO... otherDAOs) {
        setConnection(dao, otherDAOs);
    }

    public DAOManager(boolean isTransactional, GenericDAO dao, GenericDAO... otherDAOs) {
        this.isTransactional = isTransactional;

        setConnection(dao, otherDAOs);
    }

    public void beginTransaction() {
        if (isTransactional) {
            try {
                connection.setAutoCommit(false);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception while trying to begin transaction.", e);
            }
        }
    }

    public void endTransaction() {
        if (isTransactional) {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                logger.log(Level.ERROR, "Exception while trying to end transaction.", e);
            }
        }

        ConnectionPool.getInstance().returnConnection(connection);
    }

    public void commit() {
        try {
            connection.commit();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Commit error.", e);
        }
    }

    public void rollback() {
        try {
            connection.rollback();
        } catch (SQLException e) {
            logger.log(Level.ERROR, "Rollback error.", e);
        }
    }

    @Override
    public void close() {
        ConnectionPool.getInstance().returnConnection(connection);
    }

    private void setConnection(GenericDAO dao, GenericDAO[] otherDAOs) {
        dao.setConnection(connection);
        for (GenericDAO anotherDAO : otherDAOs) {
            anotherDAO.setConnection(connection);
        }
    }

}