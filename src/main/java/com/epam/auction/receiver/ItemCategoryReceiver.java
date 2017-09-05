package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;

/**
 * Provides the base model interface for loading categories.
 */
public interface ItemCategoryReceiver extends Receiver {

    /**
     * Load all item categories.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not select all categories
     */
    void loadCategories(RequestContent requestContent) throws ReceiverException;

}