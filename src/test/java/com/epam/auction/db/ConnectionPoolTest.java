package com.epam.auction.db;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Ignore;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNull.notNullValue;

@Ignore
public class ConnectionPoolTest {

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
