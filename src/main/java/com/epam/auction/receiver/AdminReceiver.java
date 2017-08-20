package com.epam.auction.receiver;

import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface AdminReceiver extends Receiver {

    void loadUsers(RequestContent requestContent) throws ReceiverLayerException;

    void approveItem(RequestContent requestContent) throws ReceiverLayerException;

}