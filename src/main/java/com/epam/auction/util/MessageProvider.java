package com.epam.auction.util;

import java.util.Locale;
import java.util.ResourceBundle;

public class MessageProvider {

    public final static String BID_MADE_SUCCESSFULLY = "message.bidMadeSuccessfully";
    public final static String NOT_ENOUGH_MONEY = "message.notEnoughMoney";
    public final static String YOUR_BID_IS_WINNING = "message.yourBidIsWinning";

    public final static String SUCCESSFUL_REGISTRATION = "message.successfulRegistration";

    private final static String BUNDLE_NAME = "/localization/message";

    private ResourceBundle resourceBundle;

    public MessageProvider(Locale locale) {
        this.resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME, locale,
                ResourceBundle.Control.getNoFallbackControl(ResourceBundle.Control.FORMAT_DEFAULT));
    }

    public String getMessage(String key) {
        return resourceBundle.getString(key);
    }

}