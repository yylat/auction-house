package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverException;

public interface AdminReceiver extends Receiver {

    void loadUsers(RequestContent requestContent) throws ReceiverException;

    void approveItem(RequestContent requestContent) throws ReceiverException;

    void discardItem(RequestContent requestContent) throws ReceiverException;

}