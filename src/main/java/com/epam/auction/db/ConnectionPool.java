package com.epam.auction.db;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class ConnectionPool {

    private static final Logger LOGGER = LogManager.getLogger();

    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    private static ConnectionPool instance;

    private BlockingQueue<ProxyConnection> availableConnections;
    private BlockingQueue<ProxyConnection> busyConnections;

    private ConnectionPool() {
        DBManager dbManager = DBManager.getInstance();

        dbManager.registerDriver();

        int poolSize = dbManager.getPoolSize();
        availableConnections = new ArrayBlockingQueue<>(poolSize);
        busyConnections = new ArrayBlockingQueue<>(poolSize);


        for (int i = 0; i < poolSize; i++) {
            try {
                availableConnections.add(dbManager.getConnection());
            } catch (SQLException e) {
                LOGGER.log(Level.WARN, "Failure while creating connection.", e);
            }
        }

        if (availableConnections.size() < poolSize) {
            int lackingConnectionsAmount = poolSize - availableConnections.size();
            int attemptToAddIndex = 0;
            do {
                try {
                    availableConnections.add(dbManager.getConnection());
                } catch (SQLException e) {
                    LOGGER.log(Level.WARN, "Failure while trying to create lacking connection.", e);
                }
            } while (attemptToAddIndex < lackingConnectionsAmount);
        }

        if (availableConnections.size() < poolSize / 2) {
            LOGGER.log(Level.FATAL, "Can not initialize connection pool with proper size.");
            throw new RuntimeException();
        }

    }

    public static ConnectionPool getInstance() {
        return instance;
    }

    public static void init() {
        if (!isInitialized.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                } else {
                    LOGGER.log(Level.WARN, "Attempt to create initialized connection pool instance.");
                }

                isInitialized.set(true);
                LOGGER.log(Level.INFO, "Connection pool initialized successfully.");
            } finally {
                lock.unlock();
            }
        } else {
            LOGGER.log(Level.WARN, "Attempt to initialize already initialized connection pool.");
        }
    }

    public void destroy() {
        if (isInitialized.get()) {
            lock.lock();
            try {
                if (instance != null) {
                    clearConnectionQueue(availableConnections);
                    clearConnectionQueue(busyConnections);// надо только один вызов clearConnectionQueue()

                    DBManager.deregisterDrivers();
                } else {
                    LOGGER.log(Level.WARN, "Attempt to destroy not initialized connection pool instance.");
                }

                instance = null;
                LOGGER.log(Level.INFO, "Connection pool destroyed successfully.");
            } catch (SQLException e) {
                LOGGER.log(Level.FATAL, "Can't destroy connection pool.", e);
                throw new RuntimeException();
            } finally {
                lock.unlock();
            }
        } else {
            LOGGER.log(Level.WARN, "Attempt to destroy not initialized connection pool.");
        }
    }

    public ProxyConnection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            busyConnections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, "InterruptedException in available connection queue.", e);
        }
        return connection;
    }

    public void returnConnection(ProxyConnection connection) {
        if (connection != null && busyConnections.contains(connection)) {
            try {
                if (!connection.getAutoCommit()) {
                    connection.setAutoCommit(true);
                }
            } catch (SQLException e) {
                LOGGER.log(Level.WARN, "Error while trying to check autocommit.");
            }
            busyConnections.remove(connection);
            availableConnections.add(connection);
        } else {
            LOGGER.log(Level.WARN, "Connection pool doesn't contain transferred connection.");
        }
    }

    private void clearConnectionQueue(BlockingQueue<ProxyConnection> connectionQueue) throws SQLException {
        ProxyConnection connection;
        while ((connection = connectionQueue.poll()) != null) {
            if (!connection.isClosed()) {
                connection.trueClose();
            }
        }
    }

}