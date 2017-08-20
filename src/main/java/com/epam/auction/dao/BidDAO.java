package com.epam.auction.dao;

import com.epam.auction.entity.Bid;
import com.epam.auction.exception.DAOLayerException;

import java.util.List;

public interface BidDAO extends GenericDAO<Bid> {

    List<Bid> findAll(int userId) throws DAOLayerException;

}