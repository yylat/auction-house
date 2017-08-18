package com.epam.auction.receiver;

import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface AdminReceiver extends Receiver {

    boolean loadUsers(RequestContent requestContent) throws ReceiverLayerException;

    boolean approveItem(RequestContent requestContent) throws ReceiverLayerException;

}