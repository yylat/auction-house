package com.epam.auction.dao.impl;

import com.epam.auction.dao.BidDAO;
import com.epam.auction.dao.TableConstant;
import com.epam.auction.entity.Bid;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.MethodNotSupportedException;

import java.sql.*;
import java.util.List;

public class BidDAOImpl extends GenericDAOImpl<Bid> implements BidDAO {

    public BidDAOImpl() {
        super(TableConstant.BID_QUERY_FIND_ALL,
                TableConstant.BID_QUERY_FIND_BY_ID,
                null,
                TableConstant.BID_QUERY_CREATE,
                null);
    }

    @Override
    public boolean delete(int id) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("Delete bid operation not supported.");
    }

    @Override
    public boolean create(Bid entity) throws DAOLayerException {
        boolean result = false;

        try (CallableStatement statement = connection.prepareCall(TableConstant.BID_QUERY_CREATE)) {
            defineQueryAttributes(entity, statement);
            if (statement.execute()) {
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOLayerException(e);
        }

        return result;
    }

    @Override
    public boolean update(Bid entity) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("Update bid operation not supported.");
    }

    @Override
    Bid extractEntity(ResultSet resultSet) throws SQLException {
        return new Bid(
                resultSet.getInt(TableConstant.BID_COLUMN_ID),
                resultSet.getInt(TableConstant.BID_COLUMN_ITEM_ID),
                resultSet.getInt(TableConstant.BID_COLUMN_BIDDER_ID),
                resultSet.getBigDecimal(TableConstant.BID_COLUMN_BID_VALUE),
                resultSet.getBoolean(TableConstant.BID_COLUMN_IS_WINNING));
    }

    @Override
    void defineQueryAttributes(Bid entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getItemId());
        statement.setInt(2, entity.getBidderId());
        statement.setBigDecimal(3, entity.getBidValue());
    }

    @Override
    public List<Bid> findUsersBids(int userId, int offset, int limit) throws DAOLayerException {
        return findSpecificList(TableConstant.BID_QUERY_FIND_FOR_USER_LIMIT,
                statement -> {
                    statement.setInt(1, userId);
                    statement.setInt(2, offset);
                    statement.setInt(3, limit);
                });
    }

    @Override
    public int countRows(int userId) throws DAOLayerException {
        int rows = 0;

        try (PreparedStatement statement = connection.prepareStatement(TableConstant.BID_QUERY_FIND_NUMBER_FOR_USER)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                rows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DAOLayerException(e);
        }

        return rows;
    }

    @Override
    public Bid findWinning(int itemId) throws DAOLayerException {
        return findEntity(TableConstant.BID_QUERY_FIND_WINNING,
                statement -> statement.setInt(1, itemId));
    }

}