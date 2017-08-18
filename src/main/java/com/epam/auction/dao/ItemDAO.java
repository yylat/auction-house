package com.epam.auction.dao;

import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOLayerException;

import java.util.List;

public interface ItemDAO extends GenericDAO<Item> {

    List<Item> findAll(int userId) throws DAOLayerException;

    List<Item> findCertain(List<Integer> statusesId) throws DAOLayerException;

    List<Item> findItems(ItemStatus itemStatus) throws DAOLayerException;

    boolean approveItem(int itemId) throws DAOLayerException;

}