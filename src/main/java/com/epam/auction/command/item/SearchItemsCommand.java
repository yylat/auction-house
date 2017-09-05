package com.epam.auction.command.item;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.controller.RequestContent;
import com.epam.auction.command.PageAddress;
import com.epam.auction.command.PageGuide;
import com.epam.auction.command.TransferMethod;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.Receiver;
import com.epam.auction.receiver.RequestConstant;

public class SearchItemsCommand extends AbstractCommand {

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
            handleReceiverException(pageGuide, e);
        }

        return pageGuide;
    }
}