package com.epam.auction.dao.impl;

import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.TableConstant;
import com.epam.auction.dao.criteria.FilterCriteria;
import com.epam.auction.dao.criteria.OrderCriteria;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides the base model implementation for `item` table DAO.
 */
public class ItemDAOImpl extends GenericDAOImpl<Item> implements ItemDAO {

    /**
     * Constructs dao for `item` table.
     */
    public ItemDAOImpl() {
        super(TableConstant.ITEM_QUERY_FIND_ALL,
                TableConstant.ITEM_QUERY_FIND_BY_ID,
                TableConstant.ITEM_QUERY_DELETE,
                TableConstant.ITEM_QUERY_CREATE,
                TableConstant.ITEM_QUERY_UPDATE);
    }

    @Override
    Item extractEntity(ResultSet resultSet) throws SQLException {
        return new Item(
                resultSet.getLong(TableConstant.ITEM_COLUMN_ID),
                resultSet.getString(TableConstant.ITEM_COLUMN_NAME),
                resultSet.getString(TableConstant.ITEM_COLUMN_DESCRIPTION),
                resultSet.getBigDecimal(TableConstant.ITEM_COLUMN_START_PRICE),
                resultSet.getBigDecimal(TableConstant.ITEM_COLUMN_BLITZ_PRICE),
                resultSet.getBigDecimal(TableConstant.ITEM_COLUMN_ACTUAL_PRICE),
                resultSet.getDate(TableConstant.ITEM_COLUMN_START_DATE),
                resultSet.getDate(TableConstant.ITEM_COLUMN_CLOSE_DATE),
                ItemStatus.define(resultSet.getInt(TableConstant.ITEM_COLUMN_STATUS_ID)),
                resultSet.getLong(TableConstant.ITEM_COLUMN_CATEGORY_ID),
                resultSet.getLong(TableConstant.ITEM_COLUMN_SELLER_ID));
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
        statement.setLong(9, entity.getItemCategoryId());
        statement.setLong(10, entity.getSellerId());
    }

    @Override
    public void updateItemStatus(long itemId, ItemStatus itemStatus) throws DAOException {
        executeUpdate(TableConstant.ITEM_QUERY_UPDATE_STATUS, statement -> {
            statement.setInt(1, itemStatus.ordinal());
            statement.setLong(2, itemId);
        });
    }

    @Override
    public int countRows(FilterCriteria filterCriteria) throws DAOException {
        String query = TableConstant.ITEM_QUERY_FIND_ROWS_COUNT +
                filterCriteria.getWhereClause();
        return countRows(query,
                statement -> defineFilter(statement, 0, filterCriteria));
    }

    @Override
    public int countRows(long userId, FilterCriteria filterCriteria) throws DAOException {
        String query = TableConstant.ITEM_QUERY_PURCHASED_ROWS_COUNT +
                filterCriteria.getWhereClausePart();
        return countRows(query, statement -> {
            statement.setLong(1, userId);
            defineFilter(statement, 2, filterCriteria);
        });
    }

    @Override
    public List<Item> findItemsWithFilter(FilterCriteria filterCriteria, OrderCriteria orderCriteria,
                                          int offset, int limit) throws DAOException {
        String query = TableConstant.ITEM_QUERY_FIND_ALL +
                filterCriteria.getWhereClause() +
                orderCriteria.getOrderByClause() +
                TableConstant.ITEM_QUERY_LIMIT;
        return findSpecificList(query, statement -> defineFilterLimit(statement, 0, filterCriteria, offset, limit));
    }

    @Override
    public List<Item> findPurchasedItems(long userId, FilterCriteria filterCriteria, OrderCriteria orderCriteria, int offset, int limit) throws DAOException {
        String query = TableConstant.ITEM_QUERY_PURCHASED +
                filterCriteria.getWhereClausePart() +
                orderCriteria.getOrderByClause() +
                TableConstant.ITEM_QUERY_LIMIT;
        return findSpecificList(query, statement -> {
            statement.setLong(1, userId);
            defineFilterLimit(statement, 1, filterCriteria, offset, limit);
        });
    }

    /**
     * Sets filter parameters to prepared statement.
     *
     * @param statement           prepared statement
     * @param startParameterIndex index to start set parameters
     * @param filterCriteria      filter parameters
     * @throws SQLException if can not set parameters to prepared statement
     */
    private void defineFilter(PreparedStatement statement, int startParameterIndex, FilterCriteria filterCriteria)
            throws SQLException {
        for (Object value : filterCriteria.getFilterValues()) {
            statement.setObject(++startParameterIndex, value);
        }
    }

    /**
     * Sets filter, offset and limit parameters to prepared statement.
     *
     * @param statement           prepared statement
     * @param startParameterIndex index to start set parameters
     * @param filterCriteria      filter parameters
     * @param offset              offset parameter
     * @param limit               limit parameter
     * @throws SQLException if can not set parameters to prepared statement
     */
    private void defineFilterLimit(PreparedStatement statement, int startParameterIndex,
                                   FilterCriteria filterCriteria, int offset, int limit) throws SQLException {
        for (Object value : filterCriteria.getFilterValues()) {
            statement.setObject(++startParameterIndex, value);
        }
        statement.setInt(++startParameterIndex, offset);
        statement.setInt(++startParameterIndex, limit);
    }

}