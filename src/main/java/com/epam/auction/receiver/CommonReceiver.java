package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;

/**
 * Provides the base model interface for common tasks.
 */
public interface CommonReceiver extends Receiver {

    /**
     * Change locale.
     *
     * @param requestContent request content
     */
    void changeLocale(RequestContent requestContent);

}