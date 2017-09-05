package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;

/**
 * Provides the base model interface for load notifications.
 */
public interface NotificationReceiver extends Receiver {

    /**
     * Load a range of notifications for user.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select items
     */
    void loadNotifications(RequestContent requestContent) throws ReceiverException;

}