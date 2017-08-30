package com.epam.auction.dao;

import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;

public interface UserDAO extends GenericDAO<User> {

    boolean isExist(User user) throws DAOException;

    boolean isUsernameAlreadyExist(String username) throws DAOException;

    boolean isEmailAlreadyExist(String email) throws DAOException;

}