package com.epam.auction.dao;

import com.epam.auction.entity.Bid;
import com.epam.auction.exception.DAOLayerException;

import java.util.List;

public interface BidDAO extends GenericDAO<Bid> {

    List<Bid> findAll(int userId) throws DAOLayerException;

    List<Bid> findUserBids(int userId, int limit) throws DAOLayerException;

    List<Bid> findNextUserBids(int userId, int lastBidsId, int limit) throws DAOLayerException;

    List<Bid> findPrevUserBids(int userId, int firstBidsId, int limit) throws DAOLayerException;

}