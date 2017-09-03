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

    @AfterClass
    public static void destroy(){
        ConnectionPool.getInstance().destroy();
    }

    @Test
    public void initTest() {
        assertThat(ConnectionPool.getInstance(), notNullValue());
    }

}