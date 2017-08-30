package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverException;

public interface PaginationReceiver extends Receiver {

    void loadBids(RequestContent requestContent) throws ReceiverException;

    void loadNotifications(RequestContent requestContent) throws ReceiverException;

    void loadItemsForCheck(RequestContent requestContent) throws ReceiverException;

    void loadActiveItems(RequestContent requestContent) throws ReceiverException;

    void loadComingItems(RequestContent requestContent) throws ReceiverException;

    void loadPurchasedItems(RequestContent requestContent) throws ReceiverException;

    void loadUserItems(RequestContent requestContent) throws ReceiverException;

}