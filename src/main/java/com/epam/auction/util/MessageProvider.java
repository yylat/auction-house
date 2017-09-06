package com.epam.auction.util;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Provides access to localized messages from properties file.
 */
public class MessageProvider {

    public static final String BID_MADE_SUCCESSFULLY = "message.bidMadeSuccessfully";
    public static final String NOT_ENOUGH_MONEY = "message.notEnoughMoney";
    public static final String YOUR_BID_IS_WINNING = "message.yourBidIsWinning";

    public static final String SUCCESSFUL_REGISTRATION = "message.successfulRegistration";

    private static final String BUNDLE_NAME = "/localization/message";

    private ResourceBundle resourceBundle;

    /**
     * Constructs MessageProvider for locale.
     *
     * @param locale locale
     */
    public MessageProvider(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale,
                ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT));
    }

    /**
     * Returns message with key from propeties file.
     *
     * @param key message key
     * @return message with key
     */
    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

}