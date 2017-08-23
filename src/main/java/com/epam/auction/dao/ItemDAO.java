package com.epam.auction.dao;

import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOLayerException;

import java.util.List;

public interface ItemDAO extends GenericDAO<Item> {

    List<Item> findCertain(List<Integer> statusesId) throws DAOLayerException;

    boolean updateItemStatus(int itemId, ItemStatus itemStatus) throws DAOLayerException;

    List<Item> findUserItems(int userId, int limit) throws DAOLayerException;

    List<Item> findNextUserItems(int userId, int lastItemId, int limit) throws DAOLayerException;

    List<Item> findPrevUserItems(int userId, int lastItemId, int limit) throws DAOLayerException;

    List<Item> findItems(ItemStatus itemStatus, int limit) throws DAOLayerException;

    List<Item> findNextItems(ItemStatus itemStatus, int lastItemId, int limit) throws DAOLayerException;

    List<Item> findPrevItems(ItemStatus itemStatus, int lastItemId, int limit) throws DAOLayerException;

}