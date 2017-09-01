package com.epam.auction.dao;

import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;

import java.util.List;

public interface UserDAO extends GenericDAO<User> {

    boolean isExist(User user) throws DAOException;

    boolean isUsernameAlreadyExist(String username) throws DAOException;

    boolean isEmailAlreadyExist(String email) throws DAOException;

    int countRows() throws DAOException;

    List<User> findUsersWithLimit(int offset, int limit) throws DAOException;

    boolean updateUserStatus(boolean isBanned, int userId) throws DAOException;

    int countRows(String username) throws DAOException;

    List<User> findByUsername(String username, int offset, int limit) throws DAOException;

}