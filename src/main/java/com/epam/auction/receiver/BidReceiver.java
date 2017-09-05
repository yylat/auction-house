package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;

/**
 * Provides the base model interface for make and load bids.
 */
public interface BidReceiver extends Receiver {

    /**
     * Creates new bid.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not insert new bid in database
     */
    void makeBid(RequestContent requestContent) throws ReceiverException;

    /**
     * Load all bids of the user.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select all bids of the user
     */
    void loadBids(RequestContent requestContent) throws ReceiverException;

}