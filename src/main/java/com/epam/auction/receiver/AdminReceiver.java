package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;

/**
 * Provides the base model interface for administrator work.
 */
public interface AdminReceiver extends Receiver {

    /**
     * Sets new status to user (banned or not).
     *
     * @param requestContent request content
     * @throws ReceiverException if can not update user
     */
    void updateUserStatus(RequestContent requestContent) throws ReceiverException;

}