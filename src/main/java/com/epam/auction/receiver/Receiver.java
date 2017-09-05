package com.epam.auction.receiver;

import com.epam.auction.command.CommandType;
import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;

/**
 * Provides the base model interface for working with DAO and request.
 */
public interface Receiver {

    /**
     * Execute business logic operation.
     *
     * @param commandType
     * @param requestContent
     * @throws ReceiverException if can not perform operation
     */
    default void action(CommandType commandType, RequestContent requestContent) throws ReceiverException {
        commandType.doReceiver(requestContent);
    }

}