package com.epam.auction.db;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

public class ConnectionPoolTest {

    @BeforeClass
    public static void init(){
        ConnectionPool.init();
    }

    @Test
    public void initTest() {
        assertThat(ConnectionPool.getInstance(), notNullValue());
    }

    @Test
    public void takeConnectionTest(){
        ProxyConnection connection = ConnectionPool.getInstance().takeConnection();
        assertThat(connection, notNullValue());
        ConnectionPool.getInstance().returnConnection(connection);
    }

    @AfterClass
    public static void destroy(){
        ConnectionPool.getInstance().destroy();
    }

}