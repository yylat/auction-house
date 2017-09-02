package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverException;

public interface NotificationReceiver extends Receiver {

    void loadNotifications(RequestContent requestContent) throws ReceiverException;

}