package com.epam.auction.dao.impl;

import com.epam.auction.dao.UserDAO;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.ReceiverException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

    @Mock
    private UserDAO userDAO;

    @Test
    public void test() throws ReceiverException {

        User user = new User("login", "password");

        boolean isExist = false;
        try {
            when(userDAO.isExist(user)).thenReturn(true);

            isExist = userDAO.isExist(user);

        } catch (DAOException e) {
            throw new ReceiverException(e);
        }

        assertThat(isExist, is(true));

    }

}