package com.epam.auction;

import com.epam.auction.dao.impl.UserDaoImplTest;
import com.epam.auction.db.ConnectionPoolTest;
import com.epam.auction.util.DateFixerTest;
import com.epam.auction.util.StringTransformerTest;
import com.epam.auction.validator.ItemValidatorTest;
import com.epam.auction.validator.PhotoValidatorTest;
import com.epam.auction.validator.UserValidatorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@Suite.SuiteClasses({
        ConnectionPoolTest.class,
        DateFixerTest.class,
        StringTransformerTest.class,
        PhotoValidatorTest.class,
        ItemValidatorTest.class,
        UserValidatorTest.class,
        UserDaoImplTest.class
})
@RunWith(Suite.class)
public class SuiteTest {
}