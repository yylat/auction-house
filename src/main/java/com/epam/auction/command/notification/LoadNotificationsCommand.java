package com.epam.auction.command.notification;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.controller.RequestContent;
import com.epam.auction.command.PageAddress;
import com.epam.auction.command.PageGuide;
import com.epam.auction.command.TransferMethod;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.Receiver;

public class LoadNotificationsCommand extends AbstractCommand {

    public LoadNotificationsCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public PageGuide execute(RequestContent requestContent) {
        PageGuide pageGuide = new PageGuide(PageAddress.NOTIFICATIONS, TransferMethod.FORWARD);

        try {
            doAction(requestContent);
        } catch (ReceiverException e) {
            handleReceiverException(pageGuide, e);
        }

        return pageGuide;
    }
}
