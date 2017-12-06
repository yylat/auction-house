package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.ReceiverException;

/**
 * Provides the base model interface for load, update, create, delete and search items..
 */
public interface ItemReceiver extends Receiver {

    /**
     * Creates new item.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not create new item
     */
    void createItem(RequestContent requestContent) throws ReceiverException;

    /**
     * Loads item.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select item
     */
    void loadItem(RequestContent requestContent) throws ReceiverException;

    /**
     * Updates item.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not update item
     */
    void updateItem(RequestContent requestContent) throws ReceiverException;

    /**
     * Adds new photos of the item.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not insert new photos item
     */
    void addPhotos(RequestContent requestContent) throws ReceiverException;

    /**
     * Deletes item.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not delete item
     */
    void deleteItem(RequestContent requestContent) throws ReceiverException;

    /**
     * Updates item: sets item status to {@link ItemStatus#CANCELED}.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not update item
     */
    void cancelAuction(RequestContent requestContent) throws ReceiverException;

    /**
     * Updates item: sets item status to {@link ItemStatus#CONFIRMED}.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not update item
     */
    void approveItem(RequestContent requestContent) throws ReceiverException;

    /**
     * Updates item: sets item status to {@link ItemStatus#NOT_CONFIRMED}.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not update item
     */
    void discardItem(RequestContent requestContent) throws ReceiverException;

    /**
     * Loads a range of items with item status {@link ItemStatus#CREATED}.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select items
     */
    void loadItemsForCheck(RequestContent requestContent) throws ReceiverException;

    /**
     * Loads a range of items with item status {@link ItemStatus#ACTIVE}.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select items
     */
    void loadActiveItems(RequestContent requestContent) throws ReceiverException;

    /**
     * Loads a range of items with item status {@link ItemStatus#CONFIRMED}.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select items
     */
    void loadComingItems(RequestContent requestContent) throws ReceiverException;

    /**
     * Load a range of items, that user won.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select items
     */
    void loadPurchasedItems(RequestContent requestContent) throws ReceiverException;

    /**
     * Load a range of items, that user put up for an auction.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select items
     */
    void loadUserItems(RequestContent requestContent) throws ReceiverException;

    /**
     * Load a range of items with name.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select items
     */
    void searchItems(RequestContent requestContent) throws ReceiverException;

    void confirmDelivery(RequestContent requestContent) throws ReceiverException;

    void reportViolation(RequestContent requestContent) throws ReceiverException;

}