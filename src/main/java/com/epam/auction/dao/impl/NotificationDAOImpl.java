package com.epam.auction.dao.impl;

import com.epam.auction.constant.TableConstant;
import com.epam.auction.dao.NotificationDAO;
import com.epam.auction.entity.Notification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationDAOImpl extends GenericDAOImpl<Notification> implements NotificationDAO {

    public NotificationDAOImpl() {
        super(TableConstant.Notification.QUERY_FIND_ALL,
                TableConstant.Notification.QUERY_FIND_BY_ID,
                TableConstant.Notification.QUERY_DELETE,
                TableConstant.Notification.QUERY_CREATE,
                TableConstant.Notification.QUERY_UPDATE);
    }

    @Override
    Notification extractEntity(ResultSet resultSet) throws SQLException {
        return new Notification(
                resultSet.getInt(TableConstant.Notification.COLUMN_ID),
                resultSet.getString(TableConstant.Notification.COLUMN_DESCRIPTION),
                resultSet.getInt(TableConstant.Notification.COLUMN_USER_ID),
                resultSet.getInt(TableConstant.Notification.COLUMN_ITEM_ID));
    }

    @Override
    void defineQueryAttributes(Notification entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getDescription());
        statement.setInt(2, entity.getUserId());
        statement.setInt(3, entity.getItemId());
    }

}
