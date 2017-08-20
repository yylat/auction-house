package com.epam.auction.receiver;

import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface BidReceiver extends Receiver {

    void loadBids(RequestContent requestContent) throws ReceiverLayerException;

}