package com.epam.auction.dao.impl;

import com.epam.auction.constant.TableConstant;
import com.epam.auction.dao.BidDAO;
import com.epam.auction.entity.Bid;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.MethodNotSupportedException;

import java.sql.*;

public class BidDAOImpl extends GenericDAOImpl<Bid> implements BidDAO {

    public BidDAOImpl() {
        super(TableConstant.Bid.QUERY_FIND_ALL,
                TableConstant.Bid.QUERY_FIND_BY_ID,
                null,
                TableConstant.Bid.QUERY_CREATE,
                null);
    }

    @Override
    public boolean delete(int id) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("Delete bid operation not supported.");
    }

    @Override
    public boolean delete(Bid entity) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("Delete bid operation not supported.");
    }

    @Override
    public boolean create(Bid entity) throws DAOLayerException {
        boolean result = false;

        try (CallableStatement statement = connection.prepareCall(TableConstant.Bid.QUERY_CREATE)) {
            defineQueryAttributes(entity, statement);
            statement.registerOutParameter(4, Types.INTEGER);
            if (statement.execute()) {
                entity.setId(statement.getInt(4));
                result = true;
            }
        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
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
                resultSet.getInt(TableConstant.Bid.COLUMN_ID),
                resultSet.getInt(TableConstant.Bid.COLUMN_ITEM_ID),
                resultSet.getInt(TableConstant.Bid.COLUMN_BIDDER_ID),
                resultSet.getBigDecimal(TableConstant.Bid.COLUMN_BID_VALUE),
                resultSet.getBoolean(TableConstant.Bid.COLUMN_IS_WINNING));
    }

    @Override
    void defineQueryAttributes(Bid entity, PreparedStatement statement) throws SQLException {
        statement.setInt(1, entity.getItemId());
        statement.setInt(2, entity.getBidderId());
        statement.setBigDecimal(3, entity.getBidValue());
    }

}