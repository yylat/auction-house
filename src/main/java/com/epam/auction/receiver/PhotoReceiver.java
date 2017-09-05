package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;

public interface PhotoReceiver extends Receiver {

    void loadPhoto(RequestContent requestContent) throws ReceiverException;

    void loadAllPhotos(RequestContent requestContent) throws ReceiverException;

    void loadPhotosForDelete(RequestContent requestContent) throws ReceiverException;

    void deletePhotos(RequestContent requestContent) throws ReceiverException;

}