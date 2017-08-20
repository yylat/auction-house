package com.epam.auction.receiver;

import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface ItemReceiver extends Receiver {

    void loadUserItems(RequestContent requestContent) throws ReceiverLayerException;

    void loadCategories(RequestContent requestContent) throws ReceiverLayerException;

    void createItem(RequestContent requestContent) throws ReceiverLayerException;

    void loadImage(RequestContent requestContent) throws ReceiverLayerException;

    void loadItemsForCheck(RequestContent requestContent) throws ReceiverLayerException;

    void loadActiveItems(RequestContent requestContent) throws ReceiverLayerException;

    void loadAllImages(RequestContent requestContent) throws ReceiverLayerException;

    void loadItem(RequestContent requestContent) throws ReceiverLayerException;

}