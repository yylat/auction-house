package com.epam.auction.command.user;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.command.RequestContent;
import com.epam.auction.controller.PageAddress;
import com.epam.auction.controller.PageGuide;
import com.epam.auction.controller.TransferMethod;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.Receiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeUsernameCommand extends AbstractCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    public ChangeUsernameCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public PageGuide execute(RequestContent requestContent) {
        PageGuide pageGuide = new PageGuide(PageAddress.ACCOUNT, TransferMethod.REDIRECT);

        try {
            doAction(requestContent);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e);
        }

        return pageGuide;
    }
}
