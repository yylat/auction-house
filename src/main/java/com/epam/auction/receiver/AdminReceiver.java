package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverException;

public interface AdminReceiver extends Receiver {

    void updateUserStatus(RequestContent requestContent) throws ReceiverException;

}