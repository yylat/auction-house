package com.epam.auction.command.item;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.command.RequestContent;
import com.epam.auction.controller.PageAddress;
import com.epam.auction.controller.PageGuide;
import com.epam.auction.controller.TransferMethod;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.Receiver;
import com.epam.auction.receiver.RequestConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SearchItemsCommand extends AbstractCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    public SearchItemsCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public PageGuide execute(RequestContent requestContent) {
        String page = (String) requestContent.getSessionAttribute(RequestConstant.CURRENT_PAGE);
        PageGuide pageGuide = new PageGuide();
        pageGuide.setTransferMethod(TransferMethod.FORWARD);
        if (page != null) {
            pageGuide.setPageAddress(page);
        } else {
            pageGuide.setPageAddress(PageAddress.ACTIVE_ITEMS);
        }

        try {
            doAction(requestContent);
        } catch (ReceiverException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
        }

        return pageGuide;
    }
}