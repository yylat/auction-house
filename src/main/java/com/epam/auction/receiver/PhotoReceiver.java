package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface PhotoReceiver extends Receiver {

    void loadPhoto(RequestContent requestContent) throws ReceiverLayerException;

    void loadAllPhotos(RequestContent requestContent) throws ReceiverLayerException;

}