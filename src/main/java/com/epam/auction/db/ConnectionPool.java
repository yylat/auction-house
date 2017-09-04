package com.epam.auction.db;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Provides a pool of connections to database. Singleton.
 */
public class ConnectionPool {

    /**
     * Logger to write logs.
     */
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Reentrant lock.
     */
    private static ReentrantLock lock = new ReentrantLock();
    /**
     * Shows if instance initialized or not.
     */
    private static AtomicBoolean isInitialized = new AtomicBoolean(false);
    /**
     * Instance of ConnectionPool
     */
    private static ConnectionPool instance;

    /**
     * Available connections queue.
     */
    private BlockingQueue<ProxyConnection> availableConnections;
    /**
     * Busy connections queue.
     */
    private BlockingQueue<ProxyConnection> busyConnections;

    /**
     * Constructs ConnectionPool. Fills available connections queue.
     * If pool not filed with given number of connections: tries to fill.
     * If pool filled with connections less then two times from given:
     * end application work by throwing RuntimeException.
     */
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
                attemptToAddIndex++;
            } while (attemptToAddIndex < lackingConnectionsAmount);
        }

        if (availableConnections.size() < poolSize / 2) {
            LOGGER.log(Level.FATAL, "Can not initialize connection pool with proper size.");
            throw new RuntimeException();
        }

    }

    /**
     * Returns ConnectionPool instance.
     *
     * @return instance
     */
    public static ConnectionPool getInstance() {
        return instance;
    }

    /**
     * Initialize ConnectionPool with double-checked locking.
     */
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

    /**
     * Destroys ConnectionPool.
     */
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

    /**
     * Returns connection from available connections
     * queue and put this connection in busy connections queue.
     *
     * @return connection from available connections queue
     */
    ProxyConnection takeConnection() {
        ProxyConnection connection = null;
        try {
            connection = availableConnections.take();
            busyConnections.put(connection);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARN, "InterruptedException in available connection queue.", e);
        }
        return connection;
    }

    /**
     * Returns connection to available connections queue if this connection
     * is not null value and busy connection queue contains it. Checks if
     * auto commit parameter is <code>false</code> and set it to <code>true</code>
     * if so. Remove connection from busy connections queue.
     *
     * @param connection connection to return
     */
    void returnConnection(ProxyConnection connection) {
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

    /**
     * Clears connection queue and closes connections.
     *
     * @param connectionQueue connection queue
     * @throws SQLException if can't close the connection
     */
    private void clearConnectionQueue(BlockingQueue<ProxyConnection> connectionQueue) throws SQLException {
        ProxyConnection connection;
        while ((connection = connectionQueue.poll()) != null) {
            if (!connection.isClosed()) {
                connection.trueClose();
            }
        }
    }

}