package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface BidReceiver extends Receiver {

    void makeBid(RequestContent requestContent) throws ReceiverLayerException;

    void loadBids(RequestContent requestContent) throws ReceiverLayerException;

}