package com.epam.auction.dao.impl;

import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.TableConstant;
import com.epam.auction.dao.criteria.FilterCriteria;
import com.epam.auction.dao.criteria.OrderCriteria;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemDAOImpl extends GenericDAOImpl<Item> implements ItemDAO {

    public ItemDAOImpl() {
        super(TableConstant.ITEM_QUERY_FIND_ALL,
                TableConstant.ITEM_QUERY_FIND_BY_ID,
                null,
                TableConstant.ITEM_QUERY_CREATE,
                TableConstant.ITEM_QUERY_UPDATE);
    }

    public boolean delete(int id) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("Delete item operation not supported.");
    }

    @Override
    Item extractEntity(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getInt(TableConstant.ITEM_COLUMN_ID),
                resultSet.getString(TableConstant.ITEM_COLUMN_NAME),
                resultSet.getString(TableConstant.ITEM_COLUMN_DESCRIPTION),
                resultSet.getBigDecimal(TableConstant.ITEM_COLUMN_START_PRICE),
                resultSet.getBigDecimal(TableConstant.ITEM_COLUMN_BLITZ_PRICE),
                resultSet.getBigDecimal(TableConstant.ITEM_COLUMN_ACTUAL_PRICE),
                resultSet.getDate(TableConstant.ITEM_COLUMN_START_DATE),
                resultSet.getDate(TableConstant.ITEM_COLUMN_CLOSE_DATE),
                ItemStatus.define(resultSet.getInt(TableConstant.ITEM_COLUMN_STATUS_ID)),
                resultSet.getInt(TableConstant.ITEM_COLUMN_CATEGORY_ID),
                resultSet.getInt(TableConstant.ITEM_COLUMN_SELLER_ID));
    }

    @Override
    void defineQueryAttributes(Item entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getName());
        statement.setString(2, entity.getDescription());
        statement.setBigDecimal(3, entity.getStartPrice());
        statement.setBigDecimal(4, entity.getBlitzPrice());
        statement.setBigDecimal(5, entity.getActualPrice());
        statement.setDate(6, entity.getStartDate());
        statement.setDate(7, entity.getCloseDate());
        statement.setInt(8, entity.getStatus().ordinal());
        statement.setInt(9, entity.getItemCategoryId());
        statement.setInt(10, entity.getSellerId());
    }

    @Override
    public boolean updateItemStatus(int itemId, ItemStatus itemStatus) throws DAOException {
        return executeUpdate(TableConstant.ITEM_QUERY_UPDATE_STATUS, statement -> {
            statement.setInt(1, itemStatus.ordinal());
            statement.setInt(2, itemId);
        });
    }

    @Override
    public int countRows(int userId) throws DAOException {
        return countRows(TableConstant.ITEM_QUERY_FIND_NUMBER_FOR_USER,
                statement -> statement.setInt(1, userId));
    }

    @Override
    public int countRows(FilterCriteria filterCriteria) throws DAOException {
        String query = TableConstant.ITEM_QUERY_FIND_ROWS_COUNT +
                filterCriteria.buildWhereClause();
        return countRows(query,
                statement -> defineFilter(statement, 0, filterCriteria));
    }

    @Override
    public int countRows(int userId, FilterCriteria filterCriteria) throws DAOException {
        String query = TableConstant.ITEM_QUERY_PURCHASED_ROWS_COUNT +
                filterCriteria.buildWhereClausePart();
        return countRows(query, statement -> {
            statement.setInt(1, userId);
            defineFilter(statement, 2, filterCriteria);
        });
    }

    @Override
    public List<Item> findItemsWithFilter(FilterCriteria filterCriteria, OrderCriteria orderCriteria,
                                          int offset, int limit) throws DAOException {
        String query = TableConstant.ITEM_QUERY_FIND_ALL +
                filterCriteria.buildWhereClause() +
                orderCriteria.getQueryPart() +
                TableConstant.ITEM_QUERY_LIMIT;
        return findSpecificList(query, statement -> defineFilterLimit(statement, 0, filterCriteria, offset, limit));
    }

    @Override
    public List<Item> findPurchasedItems(int userId, FilterCriteria filterCriteria, OrderCriteria orderCriteria, int offset, int limit) throws DAOException {
        String query = TableConstant.ITEM_QUERY_PURCHASED +
                filterCriteria.buildWhereClausePart() +
                orderCriteria.getQueryPart() +
                TableConstant.ITEM_QUERY_LIMIT;
        return findSpecificList(query, statement -> {
            statement.setInt(1, userId);
            defineFilterLimit(statement, 1, filterCriteria, offset, limit);
        });
    }

    private void defineFilter(PreparedStatement statement, int startParameterIndex, FilterCriteria filterCriteria)
            throws SQLException {
        for (Object value : filterCriteria.getValues()) {
            statement.setObject(++startParameterIndex, value);
        }
    }

    private void defineFilterLimit(PreparedStatement statement, int startParameterIndex,
                                   FilterCriteria filterCriteria, int offset, int limit) throws SQLException {
        for (Object value : filterCriteria.getValues()) {
            statement.setObject(++startParameterIndex, value);
        }
        statement.setInt(++startParameterIndex, offset);
        statement.setInt(++startParameterIndex, limit);
    }


}