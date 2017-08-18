package com.epam.auction.pool;

public class Test {

    @org.junit.Test
    public void test(){
        t(1);
        t(1, 2, 3);
    }

    private void t(int x, int... xs){
        ti(x, xs);
    }

    private void ti(int x, int[] xs){
        System.out.println(x);
        for (int i : xs) {
            System.out.println(i);
        }
    }

}