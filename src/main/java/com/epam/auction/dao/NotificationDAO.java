package com.epam.auction.dao;

import com.epam.auction.entity.Notification;
import com.epam.auction.exception.DAOException;

import java.util.List;

public interface NotificationDAO extends GenericDAO<Notification> {

    List<Notification> findUsersNotifications(int userId, int offset, int limit) throws DAOException;

    int countRows(int userId) throws DAOException;

}