package com.epam.auction.dao;

import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;

import java.util.List;

public interface UserDAO extends GenericDAO<User> {

    boolean isExist(User user) throws DAOException;

    boolean isUsernameAlreadyExist(String username) throws DAOException;

    boolean isEmailAlreadyExist(String email) throws DAOException;

    int countRows(int userId) throws DAOException;

    List<User> findUsersWithLimit(int userId, int offset, int limit) throws DAOException;

    boolean updateUserStatus(boolean isBanned, int userId) throws DAOException;

}