package com.epam.auction.pool;

import com.epam.auction.db.ConnectionPool;

public class Test {

    @org.junit.Test
    public void test(){
        ConnectionPool.init();
       ConnectionPool.getInstance().takeConnection();
        ConnectionPool.getInstance().destroy();
    }

}