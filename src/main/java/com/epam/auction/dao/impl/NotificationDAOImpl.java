package com.epam.auction.dao.impl;

import com.epam.auction.dao.NotificationDAO;
import com.epam.auction.dao.TableConstant;
import com.epam.auction.entity.Notification;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class NotificationDAOImpl extends GenericDAOImpl<Notification> implements NotificationDAO {

    public NotificationDAOImpl() {
        super(TableConstant.NOTIFICATION_QUERY_FIND_ALL,
                TableConstant.NOTIFICATION_QUERY_FIND_BY_ID,
                null,
                null,
                null);
    }

    @Override
    Notification extractEntity(ResultSet resultSet) throws SQLException {
        return new Notification(
                resultSet.getInt(TableConstant.NOTIFICATION_COLUMN_ID),
                Notification.NotificationType.define(resultSet.getInt(TableConstant.NOTIFICATION_COLUMN_TYPE)),
                resultSet.getInt(TableConstant.NOTIFICATION_COLUMN_USER_ID),
                resultSet.getInt(TableConstant.NOTIFICATION_COLUMN_ITEM_ID),
                resultSet.getTimestamp(TableConstant.NOTIFICATION_COLUMN_DATE_TIME));
    }

    @Override
    void defineQueryAttributes(Notification entity, PreparedStatement statement) throws SQLException {

    }

    public boolean delete(int id) throws DAOException, MethodNotSupportedException {
        throw new MethodNotSupportedException();
    }

    public boolean create(Notification entity) throws DAOException {
        return false;
    }

    public boolean update(Notification entity) throws DAOException, MethodNotSupportedException {
        throw new MethodNotSupportedException();
    }

    @Override
    public List<Notification> findUsersNotifications(int userId, int offset, int limit) throws DAOException {
        return findSpecificList(TableConstant.NOTIFICATION_QUERY_FIND_FOR_USER_LIMIT,
                statement -> {
                    statement.setInt(1, userId);
                    statement.setInt(2, offset);
                    statement.setInt(3, limit);
                });
    }

    @Override
    public int countRows(int userId) throws DAOException {
        return countRows(TableConstant.NOTIFICATION_QUERY_FIND_NUMBER_FOR_USER,
                statement -> statement.setInt(1, userId));
    }

}