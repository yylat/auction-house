package com.epam.auction.dao;

import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;

import java.util.List;

/**
 * Provides the base model DAO interface for `user` table.
 */
public interface UserDAO extends GenericDAO<User> {

    /**
     * Returns <code>true</code> if user exists in database.
     *
     * @param user user
     * @return <code>true</code> if user exists in database;
     * <code>false</code> otherwise
     * @throws DAOException if SQL exception occurred
     */
    boolean isExist(User user) throws DAOException;

    /**
     * Returns <code>true</code> if username exists in database.
     *
     * @param username username
     * @return <code>true</code> if username exists in database;
     * <code>false</code> otherwise
     * @throws DAOException if SQL exception occurred
     */
    boolean isUsernameAlreadyExist(String username) throws DAOException;

    /**
     * Returns <code>true</code> if email exists in database.
     *
     * @param email email
     * @return <code>true</code> if email exists in database;
     * <code>false</code> otherwise
     * @throws DAOException if SQL exception occurred
     */
    boolean isEmailAlreadyExist(String email) throws DAOException;

    /**
     * Returns the number of all the users with role user {@link com.epam.auction.entity.User.UserRole#USER}.
     *
     * @return the number of all the users with role user
     * @throws DAOException if SQL exception occurred
     */
    int countRows() throws DAOException;

    /**
     * Returns a range of all the users with role user {@link com.epam.auction.entity.User.UserRole#USER}.
     *
     * @param offset offset
     * @param limit  limit
     * @return the range of all the users with role user
     * @throws DAOException if SQL exception occurred
     */
    List<User> findUsersWithLimit(int offset, int limit) throws DAOException;

    /**
     * Updates ban status of the user with user id.
     *
     * @param isBanned ban status
     * @param userId   user id
     * @throws DAOException if SQL exception occurred
     */
    void updateUserStatus(boolean isBanned, int userId) throws DAOException;

    /**
     * Returns the number of all the users with username
     * and role user {@link com.epam.auction.entity.User.UserRole#USER}.
     *
     * @param username username
     * @return the number of all the users with username
     * and role user
     * @throws DAOException if SQL exception occurred
     */
    int countRows(String username) throws DAOException;

    /**
     * Returns a range of all the users with username
     * and role user {@link com.epam.auction.entity.User.UserRole#USER}.
     *
     * @param username username
     * @param offset   offset
     * @param limit    limit
     * @return the range of all the users with username
     * and role user
     * @throws DAOException if SQL exception occurred
     */
    List<User> findByUsername(String username, int offset, int limit) throws DAOException;

}