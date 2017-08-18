package com.epam.auction.db;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

class DBManager {

    private static final Logger logger = LogManager.getLogger();

    private static final String BUNDLE_NAME = "config.database";

    private static final String PROP_PREFIX = "db.";
    private static final String DRIVER_PROP_NAME = "driver";
    private static final String URL_PROP_NAME = "url";
    private static final String USER_PROP_NAME = "user";
    private static final String PASSWORD_PROP_NAME = "password";
    private static final String POOL_SIZE_PROP_NAME = "poolSize";
    private static final String USE_UNICODE_PROP_NAME = "useUnicode";
    private static final String ENCODING_PROP_NAME = "encoding";
    private static final String USE_SSL_PROP_NAME = "useSSL";

    private static final String DEFAULT_URL = "jdbc:mysql://localhost:3306/auction";
    private static final String DEFAULT_USE_UNICODE = "true";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String DEFAULT_USE_SSL = "false";
    private static final String DEFAULT_DRIVER_NAME = "com.mysql.jdbc.Driver";
    private static final int DEFAULT_POOL_SIZE = 10;

    private static DBManager instance;

    private String url;
    private Properties dbInfo;

    private int poolSize;
    private String driverName;

    private DBManager() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

        dbInfo = new Properties();

        try {
            dbInfo.setProperty(USER_PROP_NAME, resourceBundle.getString(PROP_PREFIX + USER_PROP_NAME));
            dbInfo.setProperty(PASSWORD_PROP_NAME, resourceBundle.getString(PROP_PREFIX + PASSWORD_PROP_NAME));
        } catch (MissingResourceException e) {
            logger.log(Level.FATAL, e);
            throw new RuntimeException();
        }

        try {
            url = resourceBundle.getString(PROP_PREFIX + URL_PROP_NAME);
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, e);
            url = DEFAULT_URL;
        }

        try {
            dbInfo.setProperty(USE_UNICODE_PROP_NAME, resourceBundle.getString(PROP_PREFIX + USE_UNICODE_PROP_NAME));
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, e);
            dbInfo.setProperty(USE_UNICODE_PROP_NAME, DEFAULT_USE_UNICODE);
        }

        try {
            dbInfo.setProperty(ENCODING_PROP_NAME, resourceBundle.getString(PROP_PREFIX + ENCODING_PROP_NAME));
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, e);
            dbInfo.setProperty(ENCODING_PROP_NAME, DEFAULT_ENCODING);
        }

        try {
            dbInfo.setProperty(USE_SSL_PROP_NAME, resourceBundle.getString(PROP_PREFIX + USE_SSL_PROP_NAME));
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, e);
            dbInfo.setProperty(USE_SSL_PROP_NAME, DEFAULT_USE_SSL);
        }

        try {
            poolSize = Integer.parseInt(resourceBundle.getString(PROP_PREFIX + POOL_SIZE_PROP_NAME));
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, e);
            poolSize = DEFAULT_POOL_SIZE;
        }

        try {
            driverName = resourceBundle.getString(PROP_PREFIX + DRIVER_PROP_NAME);
        } catch (MissingResourceException e) {
            logger.log(Level.ERROR, e);
            driverName = DEFAULT_DRIVER_NAME;
        }

    }

    static DBManager getInstance() {
        if (instance == null) {
            instance = new DBManager();
        }
        return instance;
    }

    void registerDriver() {
        try {
            Class.forName(driverName);
        } catch (ClassNotFoundException e) {
            logger.log(Level.FATAL, "DB driver class not found.", e);
            throw new RuntimeException();
        }
    }

    int getPoolSize() {
        return poolSize;
    }

    ProxyConnection getConnection() throws SQLException {
        return new ProxyConnection(DriverManager.getConnection(url, dbInfo));
    }

    static void deregisterDrivers() {
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        while (drivers.hasMoreElements()) {
            Driver driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException e) {
                logger.log(Level.FATAL, e);
                throw new RuntimeException();
            }
        }
    }

}