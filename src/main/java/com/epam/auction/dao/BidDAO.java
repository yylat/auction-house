package com.epam.auction.dao;

import com.epam.auction.entity.Bid;
import com.epam.auction.exception.DAOLayerException;

import java.util.List;

public interface BidDAO extends GenericDAO<Bid> {

    List<Bid> findUsersBids(int userId, int offset, int limit) throws DAOLayerException;

    int countRows(int userId) throws DAOLayerException;

    Bid findWinning(int itemId) throws DAOLayerException;

}