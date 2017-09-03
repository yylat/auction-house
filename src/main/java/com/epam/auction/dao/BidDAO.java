package com.epam.auction.dao;

import com.epam.auction.entity.Bid;
import com.epam.auction.exception.DAOException;

import java.util.List;

/**
 * Provides the base model DAO interface for `bid` table.
 */
public interface BidDAO extends GenericDAO<Bid> {

    /**
     * Returns a range of all the bids with bidder id.
     *
     * @param bidderId bidder id
     * @param offset   offset
     * @param limit    limit
     * @return the range of all the bids of the user with user id
     * @throws DAOException if SQL exception occurred
     */
    List<Bid> findUsersBids(long bidderId, int offset, int limit) throws DAOException;

    /**
     * Returns the number of all the bids with bidder id.
     *
     * @param bidderId user id
     * @return the number of all the bids with bidder id
     * @throws DAOException if SQL exception occurred
     */
    int countRows(long bidderId) throws DAOException;

    /**
     * Returns winning bid with item id.
     *
     * @param itemId item id
     * @return winning bid with item id
     * @throws DAOException if SQL exception occurred
     */
    Bid findWinning(int itemId) throws DAOException;

}