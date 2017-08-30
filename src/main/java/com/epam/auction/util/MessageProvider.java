package com.epam.auction.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageProvider {

    public static final String BID_MADE_SUCCESSFULLY = "message.bidMadeSuccessfully";
    public static final String NOT_ENOUGH_MONEY = "message.notEnoughMoney";
    public static final String YOUR_BID_IS_WINNING = "message.yourBidIsWinning";

    public static final String SUCCESSFUL_REGISTRATION = "message.successfulRegistration";

    private static final String BUNDLE_NAME = "/localization/message";

    private ResourceBundle resourceBundle;

    public MessageProvider(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale,
                ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT));
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

}