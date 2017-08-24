package com.epam.auction.dao;

import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOLayerException;

import java.util.List;

public interface ItemDAO extends GenericDAO<Item> {

    List<Item> findCertain(List<Integer> statusesId) throws DAOLayerException;

    boolean updateItemStatus(int itemId, ItemStatus itemStatus) throws DAOLayerException;

    List<Item> findUsersItemsLimit(int userId, int offset, int limit) throws DAOLayerException;

    List<Item> findItemsWithStatusLimit(ItemStatus itemStatus, int offset, int limit) throws DAOLayerException;

    int countRows(int userId) throws DAOLayerException;

    int countRows(ItemStatus itemStatus) throws DAOLayerException;

}