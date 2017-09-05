package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;

public interface BidReceiver extends Receiver {

    void makeBid(RequestContent requestContent) throws ReceiverException;

    void loadBids(RequestContent requestContent) throws ReceiverException;

}