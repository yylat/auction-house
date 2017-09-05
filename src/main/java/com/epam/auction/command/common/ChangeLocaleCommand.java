package com.epam.auction.command.common;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.controller.RequestContent;
import com.epam.auction.command.PageGuide;
import com.epam.auction.command.TransferMethod;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.Receiver;
import com.epam.auction.receiver.RequestConstant;

public class ChangeLocaleCommand extends AbstractCommand {

    public ChangeLocaleCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public PageGuide execute(RequestContent requestContent) {
        String pageAddress = (String) requestContent.getSessionAttribute(RequestConstant.CURRENT_PAGE);
        PageGuide pageGuide = new PageGuide(pageAddress, TransferMethod.FORWARD);

        try {
            doAction(requestContent);
        } catch (ReceiverException e) {
            handleReceiverException(pageGuide, e);
        }

        return pageGuide;
    }
}
