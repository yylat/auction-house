package com.epam.auction.dao;

import com.epam.auction.entity.Notification;
import com.epam.auction.exception.DAOException;

import java.util.List;

/**
 * Provides the base model DAO interface for `notification` table.
 */
public interface NotificationDAO extends GenericDAO<Notification> {

    /**
     * Returns a range of all the notifications with user id.
     *
     * @param userId user id
     * @param offset offset
     * @param limit  limit
     * @return the range of all the notifications with user id
     * @throws DAOException if SQL exception occurred
     */
    List<Notification> findUsersNotifications(long userId, int offset, int limit) throws DAOException;

    /**
     * Returns the number of all the notifications with user id.
     *
     * @param userId user id
     * @return the number of all the notifications with user id
     * @throws DAOException if SQL exception occurred
     */
    int countRows(long userId) throws DAOException;

}