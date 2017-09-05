package com.epam.auction.dao.impl;

import com.epam.auction.dao.BidDAO;
import com.epam.auction.dao.TableConstant;
import com.epam.auction.entity.Bid;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;

import java.sql.*;
import java.util.List;

/**
 * Provides the base model implementation for `bid` table DAO.
 */
public class BidDAOImpl extends GenericDAOImpl<Bid> implements BidDAO {

    /**
     * Constructs dao for `bid` table.
     */
    public BidDAOImpl() {
        super(TableConstant.BID_QUERY_FIND_ALL,
                TableConstant.BID_QUERY_FIND_BY_ID,
                null,
                TableConstant.BID_QUERY_CREATE,
                null);
    }

    @Override
    public void delete(long id) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("Delete bid operation not supported.");
    }

    @Override
    public void create(Bid entity) throws DAOException {
        try (CallableStatement statement = connection.prepareCall(TableConstant.BID_QUERY_CREATE)) {
            defineQueryAttributes(entity, statement);
            statement.execute();
        } catch (SQLException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Bid entity) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("Update bid operation not supported.");
    }

    @Override
    Bid extractEntity(ResultSet resultSet) throws SQLException {
        return new Bid(
                resultSet.getLong(TableConstant.BID_COLUMN_ID),
                resultSet.getLong(TableConstant.BID_COLUMN_ITEM_ID),
                resultSet.getLong(TableConstant.BID_COLUMN_BIDDER_ID),
                resultSet.getBigDecimal(TableConstant.BID_COLUMN_BID_VALUE),
                resultSet.getBoolean(TableConstant.BID_COLUMN_IS_WINNING));
    }

    @Override
    void defineQueryAttributes(Bid entity, PreparedStatement statement) throws SQLException {
        statement.setLong(1, entity.getItemId());
        statement.setLong(2, entity.getBidderId());
        statement.setBigDecimal(3, entity.getBidValue());
    }

    @Override
    public List<Bid> findUsersBids(long bidderId, int offset, int limit) throws DAOException {
        return findSpecificList(TableConstant.BID_QUERY_FIND_FOR_USER_LIMIT,
                statement -> {
                    statement.setLong(1, bidderId);
                    statement.setInt(2, offset);
                    statement.setInt(3, limit);
                });
    }

    @Override
    public int countRows(long bidderId) throws DAOException {
        return countRows(TableConstant.BID_QUERY_FIND_NUMBER_FOR_USER,
                statement -> statement.setLong(1, bidderId));
    }

    @Override
    public Bid findWinning(long itemId) throws DAOException {
        return findEntity(TableConstant.BID_QUERY_FIND_WINNING,
                statement -> statement.setLong(1, itemId));
    }

}