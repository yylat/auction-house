package com.epam.auction.db;

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