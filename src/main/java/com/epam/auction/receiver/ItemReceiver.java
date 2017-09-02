package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverException;

public interface ItemReceiver extends Receiver {

    void loadCategories(RequestContent requestContent) throws ReceiverException;

    void createItem(RequestContent requestContent) throws ReceiverException;

    void loadItem(RequestContent requestContent) throws ReceiverException;

    void updateItem(RequestContent requestContent) throws ReceiverException;

    void addPhotos(RequestContent requestContent) throws ReceiverException;

    void deleteItem(RequestContent requestContent) throws ReceiverException;

    void cancelAuction(RequestContent requestContent) throws ReceiverException;

    void approveItem(RequestContent requestContent) throws ReceiverException;

    void discardItem(RequestContent requestContent) throws ReceiverException;

    void loadItemsForCheck(RequestContent requestContent) throws ReceiverException;

    void loadActiveItems(RequestContent requestContent) throws ReceiverException;

    void loadComingItems(RequestContent requestContent) throws ReceiverException;

    void loadPurchasedItems(RequestContent requestContent) throws ReceiverException;

    void loadUserItems(RequestContent requestContent) throws ReceiverException;

}