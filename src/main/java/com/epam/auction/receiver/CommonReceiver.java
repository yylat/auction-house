package com.epam.auction.receiver;

import com.epam.auction.command.RequestContent;

public interface CommonReceiver extends Receiver {

    void changeLocale(RequestContent requestContent);

}
