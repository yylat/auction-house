package com.epam.auction.receiver;

import com.epam.auction.content.RequestContent;

public interface CommonReceiver extends Receiver {

    boolean openMainPage(RequestContent requestContent);

    boolean changeLocale(RequestContent requestContent);

}
