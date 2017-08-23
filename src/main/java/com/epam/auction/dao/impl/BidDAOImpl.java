package com.epam.auction.dao.impl;

import com.epam.auction.dao.TableConstant;
import com.epam.auction.dao.BidDAO;
import com.epam.auction.entity.Bid;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.MethodNotSupportedException;

import java.sql.*;
import java.util.ArrayList;
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
            statement.registerOutParameter(1, Types.INTEGER);
            defineQueryAttributes(entity, statement);
            if (statement.execute()) {
                entity.setId(statement.getInt(1));
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
                resultSet.getInt(TableConstant.BID_COLUMN_ID),
                resultSet.getInt(TableConstant.BID_COLUMN_ITEM_ID),
                resultSet.getInt(TableConstant.BID_COLUMN_BIDDER_ID),
                resultSet.getBigDecimal(TableConstant.BID_COLUMN_BID_VALUE),
                resultSet.getBoolean(TableConstant.BID_COLUMN_IS_WINNING));
    }

    @Override
    void defineQueryAttributes(Bid entity, PreparedStatement statement) throws SQLException {
        statement.setInt(2, entity.getItemId());
        statement.setInt(3, entity.getBidderId());
        statement.setBigDecimal(4, entity.getBidValue());
    }

    @Override
    public List<Bid> findAll(int userId) throws DAOLayerException {
        List<Bid> bids;

        try (PreparedStatement statement = connection.prepareStatement(TableConstant.BID_QUERY_FIND_ALL_FOR_USER)) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            bids = new ArrayList<>();
            while (resultSet.next()) {
                bids.add(extractEntity(resultSet));
            }
        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }

        return bids;
    }

    @Override
    public List<Bid> findUserBids(int userId, int limit) throws DAOLayerException {

        return findSpecificList(TableConstant.BID_QUERY_FIND_FOR_USER,
                statement -> {
                    statement.setInt(1, userId);
                    statement.setInt(2, limit);
                });

//        List<Bid> bids;
//
//        try (PreparedStatement statement = connection.prepareStatement(TableConstant.BID_QUERY_FIND_FOR_USER)) {
//            statement.setInt(1, userId);
//            statement.setInt(2, limit);
//            ResultSet resultSet = statement.executeQuery();
//
//            bids = new ArrayList<>();
//            while (resultSet.next()) {
//                bids.add(extractEntity(resultSet));
//            }
//        } catch (SQLException e) {
//            throw new DAOLayerException(e.getMessage(), e);
//        }
//
//        return bids;
    }

    @Override
    public List<Bid> findNextUserBids(int userId, int lastBidsId, int limit) throws DAOLayerException {

        return findSpecificList(TableConstant.BID_QUERY_FIND_NEXT_FOR_USER,
                statement -> {
                    statement.setInt(1, userId);
                    statement.setInt(2, lastBidsId);
                    statement.setInt(3, limit);
                });

//        List<Bid> bids;
//
//        try (PreparedStatement statement = connection.prepareStatement(TableConstant.BID_QUERY_FIND_NEXT_FOR_USER)) {
//            statement.setInt(1, userId);
//            statement.setInt(2, lastBidsId);
//            statement.setInt(3, limit);
//            ResultSet resultSet = statement.executeQuery();
//
//            bids = new ArrayList<>();
//            while (resultSet.next()) {
//                bids.add(extractEntity(resultSet));
//            }
//        } catch (SQLException e) {
//            throw new DAOLayerException(e.getMessage(), e);
//        }
//
//        return bids;
    }

    @Override
    public List<Bid> findPrevUserBids(int userId, int firstBidsId, int limit) throws DAOLayerException {
        return findSpecificList(TableConstant.BID_QUERY_FIND_PREV_FOR_USER,
                statement -> {
                    statement.setInt(1, userId);
                    statement.setInt(2, firstBidsId);
                    statement.setInt(3, limit);
                });
    }
}