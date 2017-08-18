package com.epam.auction.receiver;

import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface ItemReceiver extends Receiver {

    boolean loadUserItems(RequestContent requestContent) throws ReceiverLayerException;

    boolean loadCategories(RequestContent requestContent) throws ReceiverLayerException;

    boolean createItem(RequestContent requestContent) throws ReceiverLayerException;

    boolean loadImage(RequestContent requestContent) throws ReceiverLayerException;

    boolean loadCertainItems(RequestContent requestContent) throws ReceiverLayerException;

    boolean loadItemsForCheck(RequestContent requestContent) throws ReceiverLayerException;

    boolean loadActiveItems(RequestContent requestContent) throws ReceiverLayerException;

    boolean loadAllImages(RequestContent requestContent) throws ReceiverLayerException;

    boolean loadItem(RequestContent requestContent);

}