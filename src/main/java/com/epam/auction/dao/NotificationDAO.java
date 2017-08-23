package com.epam.auction.dao;

import com.epam.auction.entity.Notification;
import com.epam.auction.exception.DAOLayerException;

import java.util.List;

public interface NotificationDAO extends GenericDAO<Notification> {

    List<Notification> findUserNotifications(int userId, int limit) throws DAOLayerException;

    List<Notification> findNextUserNotifications(int userId, int lastNotificationsId, int limit) throws DAOLayerException;

    List<Notification> findPrevUserNotifications(int userId, int firstNotificationsId, int limit) throws DAOLayerException;

}