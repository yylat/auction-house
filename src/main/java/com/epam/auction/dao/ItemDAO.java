package com.epam.auction.dao;

import com.epam.auction.dao.criteria.FilterCriteria;
import com.epam.auction.dao.criteria.OrderCriteria;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOException;

import java.util.List;

/**
 * Provides the base model DAO interface for `item` table.
 */
public interface ItemDAO extends GenericDAO<Item> {

    /**
     * Updates item status with item id.
     *
     * @param itemId     item id
     * @param itemStatus item status
     * @return <code>true</code> if item status was updated successfully;
     * <code>false</code> otherwise
     * @throws DAOException if SQL exception occurred
     */
    boolean updateItemStatus(long itemId, ItemStatus itemStatus) throws DAOException;

    /**
     * Returns the number of all the items with filter criteria.
     *
     * @param filterCriteria filter criteria
     * @return the number of all the items with filter criteria
     * @throws DAOException if SQL exception occurred
     */
    int countRows(FilterCriteria filterCriteria) throws DAOException;

    /**
     * Returns the number of all the items with user id and filter criteria.
     *
     * @param userId         user id
     * @param filterCriteria filter criteria
     * @return the number of all the items with user id and filter criteria
     * @throws DAOException if SQL exception occurred
     */
    int countRows(long userId, FilterCriteria filterCriteria) throws DAOException;

    /**
     * Returns an ordered range of all the items with filter criteria.
     *
     * @param filterCriteria filter criteria
     * @param orderCriteria  order criteria
     * @param offset         offset
     * @param limit          limit
     * @return the ordered range of all the items with filter criteria.
     * @throws DAOException if SQL exception occurred
     */
    List<Item> findItemsWithFilter(FilterCriteria filterCriteria, OrderCriteria orderCriteria,
                                   int offset, int limit) throws DAOException;

    /**
     * Returns an ordered range of all the items with user id and filter criteria.
     *
     * @param userId         user id
     * @param filterCriteria filter criteria
     * @param orderCriteria  order criteria
     * @param offset         offset
     * @param limit          limit
     * @return the ordered range of all the items with user id and filter criteria
     * @throws DAOException if SQL exception occurred
     */
    List<Item> findPurchasedItems(long userId, FilterCriteria filterCriteria, OrderCriteria orderCriteria,
                                  int offset, int limit) throws DAOException;

}