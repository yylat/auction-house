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
    private static final String REFERER_HEADER = "referer";
    private static final String HOST_HEADER = "host";

    public ChangeLocaleCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public Page execute(RequestContent requestContent) {
        Page page = new Page();

        PageName pageName = (PageName) requestContent.getSessionAttribute(RequestConstant.CURRENT_PAGE);

        try {
            if (doAction(requestContent)) {
                page.setPageName(pageName);
                page.setTransferMethod(TransferMethod.FORWARD);
            }
        } catch (ReceiverLayerException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }

        return page;
    }
}
