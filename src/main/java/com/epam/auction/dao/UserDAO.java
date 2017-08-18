package com.epam.auction.dao;

import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOLayerException;

public interface UserDAO extends GenericDAO<User> {

    boolean isExist(User user) throws DAOLayerException;

    boolean isUsernameAlreadyExist(String username) throws DAOLayerException;

    boolean isEmailAlreadyExist(String email) throws DAOLayerException;

}