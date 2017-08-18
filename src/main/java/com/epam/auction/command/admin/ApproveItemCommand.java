package com.epam.auction.command.admin;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.content.RequestContent;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.page.Page;
import com.epam.auction.page.PageName;
import com.epam.auction.page.TransferMethod;
import com.epam.auction.receiver.Receiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApproveItemCommand extends AbstractCommand {

    private final static Logger logger = LogManager.getLogger();

    public ApproveItemCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public Page execute(RequestContent requestContent) {
        Page page = new Page();

        try {
            if (doAction(requestContent)) {
                page.setPageName(PageName.ITEMS_MANAGEMENT);
                page.setTransferMethod(TransferMethod.REDIRECT);
            } else {
                page.setPageName(PageName.ERROR);
                page.setTransferMethod(TransferMethod.REDIRECT);
            }
        } catch (ReceiverLayerException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }

        return page;
    }
}
