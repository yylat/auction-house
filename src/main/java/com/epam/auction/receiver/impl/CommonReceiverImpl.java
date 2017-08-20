package com.epam.auction.receiver.impl;

import com.epam.auction.constant.RequestConstant;
import com.epam.auction.content.RequestContent;
import com.epam.auction.receiver.CommonReceiver;
import com.epam.auction.util.LocaleFactory;

import java.util.Locale;

public class CommonReceiverImpl implements CommonReceiver {

    @Override
    public void changeLocale(RequestContent requestContent) {

        Locale locale = LocaleFactory.getLocale(requestContent.getRequestParameter(RequestConstant.LOCALE_LANG)[0]);

        requestContent.setSessionAttribute("locale", locale);

    }

}