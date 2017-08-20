package com.epam.auction.command.common;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.constant.RequestConstant;
import com.epam.auction.content.RequestContent;
import com.epam.auction.page.Page;
import com.epam.auction.page.TransferMethod;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.page.PageName;
import com.epam.auction.receiver.Receiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChangeLocaleCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger();

    public ChangeLocaleCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public Page execute(RequestContent requestContent) {
        PageName pageName = (PageName) requestContent.getSessionAttribute(RequestConstant.CURRENT_PAGE);
        Page page = new Page(pageName, TransferMethod.FORWARD);

        try {
            doAction(requestContent);
        } catch (ReceiverLayerException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }

        return page;
    }
}
