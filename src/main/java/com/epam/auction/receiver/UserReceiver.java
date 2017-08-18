package com.epam.auction.receiver;

import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface UserReceiver extends Receiver {

    boolean signIn(RequestContent requestContent) throws ReceiverLayerException;

    boolean signUp(RequestContent requestContent) throws ReceiverLayerException;

    boolean logOut(RequestContent requestContent);

}