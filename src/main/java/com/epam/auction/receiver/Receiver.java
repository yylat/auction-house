package com.epam.auction.receiver;

import com.epam.auction.command.CommandType;
import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface Receiver {

    default void action(CommandType commandType, RequestContent requestContent) throws ReceiverLayerException {
        commandType.doReceiver(requestContent);
    }

}