package com.epam.auction.receiver.impl;

import com.epam.auction.constant.RequestConstant;
import com.epam.auction.content.RequestContent;
import com.epam.auction.page.PageName;
import com.epam.auction.receiver.CommonReceiver;
import com.epam.auction.util.LocaleFactory;

import java.util.Arrays;
import java.util.Locale;

public class CommonReceiverImpl implements CommonReceiver {

    @Override
    public boolean openMainPage(RequestContent requestContent) {
        // add load needed resources
        return true;
    }

    @Override
    public boolean changeLocale(RequestContent requestContent) {

        Locale locale = LocaleFactory.getLocale(requestContent.getRequestParameter(RequestConstant.LOCALE_LANG)[0]);

        requestContent.setSessionAttribute("locale", locale);

        return true;
    }

}