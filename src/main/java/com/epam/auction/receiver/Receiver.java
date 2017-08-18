package com.epam.auction.receiver;

import com.epam.auction.command.CommandType;
import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface Receiver {

    default boolean action(CommandType commandType, RequestContent requestContent) throws ReceiverLayerException {
        return commandType.doReceiver(requestContent);
    }

}