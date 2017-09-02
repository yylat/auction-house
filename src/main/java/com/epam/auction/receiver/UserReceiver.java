package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;
import com.epam.auction.exception.ReceiverException;

public interface UserReceiver extends Receiver {

    void signIn(RequestContent requestContent) throws ReceiverException;

    void signUp(RequestContent requestContent) throws ReceiverException;

    void logOut(RequestContent requestContent);

    void replenishBalance(RequestContent requestContent) throws ReceiverException;

    void changeUsername(RequestContent requestContent) throws ReceiverException;

    void changeEmail(RequestContent requestContent) throws ReceiverException;

    void changePassword(RequestContent requestContent) throws ReceiverException;

    void updateProfile(RequestContent requestContent) throws ReceiverException;

    void loadUsers(RequestContent requestContent) throws ReceiverException;

    void findUsersByUsername(RequestContent requestContent) throws ReceiverException;

}