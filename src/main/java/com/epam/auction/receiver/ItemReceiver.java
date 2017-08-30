package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface ItemReceiver extends Receiver {

    void loadUserItems(RequestContent requestContent) throws ReceiverLayerException;

    void loadCategories(RequestContent requestContent) throws ReceiverLayerException;

    void createItem(RequestContent requestContent) throws ReceiverLayerException;

    void loadItemsForCheck(RequestContent requestContent) throws ReceiverLayerException;

    void loadActiveItems(RequestContent requestContent) throws ReceiverLayerException;

    void loadComingItems(RequestContent requestContent) throws ReceiverLayerException;

    void loadItem(RequestContent requestContent) throws ReceiverLayerException;

    void loadPurchasedItems(RequestContent requestContent) throws ReceiverLayerException;

    void updateItem(RequestContent requestContent) throws ReceiverLayerException;

    void addPhotos(RequestContent requestContent) throws ReceiverLayerException;

}