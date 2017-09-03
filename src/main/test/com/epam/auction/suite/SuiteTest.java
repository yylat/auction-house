package com.epam.auction.suite;

import com.epam.auction.db.ConnectionPoolTest;
import com.epam.auction.util.DateFixerTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({
        ConnectionPoolTest.class,
        DateFixerTest.class
})
@RunWith(Suite.class)
public class SuiteTest {
}