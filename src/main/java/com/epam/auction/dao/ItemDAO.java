package com.epam.auction.dao;

import com.epam.auction.dao.filter.FilterCriteria;
import com.epam.auction.dao.filter.OrderCriteria;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOLayerException;

import java.util.List;

public interface ItemDAO extends GenericDAO<Item> {

    boolean updateItemStatus(int itemId, ItemStatus itemStatus) throws DAOLayerException;

    int countRows(int userId) throws DAOLayerException;

    int countRows(FilterCriteria filterCriteria) throws DAOLayerException;

    int countRows(int userId, FilterCriteria filterCriteria) throws DAOLayerException;

    List<Item> findItemsWithFilter(FilterCriteria filterCriteria, OrderCriteria orderCriteria,
                                   int offset, int limit) throws DAOLayerException;

    List<Item> findPurchasedItems(int userId, FilterCriteria filterCriteria, OrderCriteria orderCriteria,
                                   int offset, int limit) throws DAOLayerException;

}