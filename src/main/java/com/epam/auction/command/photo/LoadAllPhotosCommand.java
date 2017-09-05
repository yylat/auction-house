package com.epam.auction.command.photo;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.controller.RequestContent;
import com.epam.auction.command.PageGuide;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.Receiver;

public class LoadAllPhotosCommand extends AbstractCommand {

    public LoadAllPhotosCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public PageGuide execute(RequestContent requestContent) {
        PageGuide pageGuide = null;

        try {
            doAction(requestContent);
        } catch (ReceiverException e) {
            pageGuide = new PageGuide();
            handleReceiverException(pageGuide, e);
        }

        return pageGuide;
    }

}