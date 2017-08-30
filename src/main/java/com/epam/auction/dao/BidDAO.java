package com.epam.auction.dao;

import com.epam.auction.entity.Bid;
import com.epam.auction.exception.DAOException;

import java.util.List;

public interface BidDAO extends GenericDAO<Bid> {

    List<Bid> findUsersBids(int userId, int offset, int limit) throws DAOException;

    int countRows(int userId) throws DAOException;

    Bid findWinning(int itemId) throws DAOException;

}