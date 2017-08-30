package com.epam.auction.receiver;

import com.epam.auction.command.CommandType;
import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverException;

public interface Receiver {

    default void action(CommandType commandType, RequestContent requestContent) throws ReceiverException {
        commandType.doReceiver(requestContent);
    }

}