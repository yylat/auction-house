package com.epam.auction.command.admin;

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

public class ApproveItemCommand extends AbstractCommand {

    private final static Logger LOGGER = LogManager.getLogger();

    public ApproveItemCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public PageGuide execute(RequestContent requestContent) {
        PageGuide pageGuide = new PageGuide(PageAddress.ITEMS_MANAGEMENT, TransferMethod.REDIRECT);

        try {
            doAction(requestContent);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
        }

        return pageGuide;
    }
}
