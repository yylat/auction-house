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

}