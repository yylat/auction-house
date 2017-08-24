package com.epam.auction.receiver.impl;

import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.command.RequestContent;
import com.epam.auction.receiver.CommonReceiver;
import com.epam.auction.util.LocaleType;

import java.util.Locale;

public class CommonReceiverImpl implements CommonReceiver {

    @Override
    public void changeLocale(RequestContent requestContent) {

        Locale locale = LocaleType.getLocale(requestContent.getRequestParameter(RequestConstant.LOCALE_LANG)[0]);

        requestContent.setSessionAttribute(RequestConstant.LOCALE, locale);

    }

}