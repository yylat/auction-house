package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;

/**
 * Provides the base model interface for authentication,
 * update, load users and replenish balance.
 */
public interface UserReceiver extends Receiver {

    /**
     * Performs authentication of the user.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select user
     */
    void signIn(RequestContent requestContent) throws ReceiverException;

    /**
     * Performs registration of the user.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not insert user
     */
    void signUp(RequestContent requestContent) throws ReceiverException;

    /**
     * Performs user log out.
     *
     * @param requestContent request content
     */
    void logOut(RequestContent requestContent);

    /**
     * Changes username of the user.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not update user
     */
    void changeUsername(RequestContent requestContent) throws ReceiverException;

    /**
     * Changes email of the user.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not update user
     */
    void changeEmail(RequestContent requestContent) throws ReceiverException;

    /**
     * Changes password of the user.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not update user
     */
    void changePassword(RequestContent requestContent) throws ReceiverException;

    /**
     * Updates general data of the user.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not update user
     */
    void updateProfile(RequestContent requestContent) throws ReceiverException;

    /**
     * Loads a range of users with user role {@link com.epam.auction.entity.User.UserRole#USER}
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select users
     */
    void loadUsers(RequestContent requestContent) throws ReceiverException;

    /**
     * Loads a range of users with user role {@link com.epam.auction.entity.User.UserRole#USER}
     * and username.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select users
     */
    void findUsersByUsername(RequestContent requestContent) throws ReceiverException;

    /**
     * Loads user's info. Used for showing seller's contact info
     * to buyer.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not load user
     */
    void showProfile(RequestContent requestContent) throws ReceiverException;
}