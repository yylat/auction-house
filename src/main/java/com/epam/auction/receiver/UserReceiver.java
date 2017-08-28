package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;

public interface UserReceiver extends Receiver {

    void signIn(RequestContent requestContent) throws ReceiverLayerException;

    void signUp(RequestContent requestContent) throws ReceiverLayerException;

    void logOut(RequestContent requestContent);

    void replenishBalance(RequestContent requestContent) throws ReceiverLayerException;

    void changeUsername(RequestContent requestContent) throws ReceiverLayerException;

    void changeEmail(RequestContent requestContent) throws ReceiverLayerException;

    void changePassword(RequestContent requestContent) throws ReceiverLayerException;

    void updateProfile(RequestContent requestContent) throws ReceiverLayerException;

}