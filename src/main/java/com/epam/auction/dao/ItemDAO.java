package com.epam.auction.dao;

import com.epam.auction.dao.filter.FilterCriteria;
import com.epam.auction.dao.filter.OrderCriteria;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOException;

import java.util.List;

public interface ItemDAO extends GenericDAO<Item> {

    boolean updateItemStatus(int itemId, ItemStatus itemStatus) throws DAOException;

    int countRows(int userId) throws DAOException;

    int countRows(FilterCriteria filterCriteria) throws DAOException;

    int countRows(int userId, FilterCriteria filterCriteria) throws DAOException;

    List<Item> findItemsWithFilter(FilterCriteria filterCriteria, OrderCriteria orderCriteria,
                                   int offset, int limit) throws DAOException;

    List<Item> findPurchasedItems(int userId, FilterCriteria filterCriteria, OrderCriteria orderCriteria,
                                   int offset, int limit) throws DAOException;

}