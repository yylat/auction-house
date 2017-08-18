package com.epam.auction.command.user;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.content.RequestContent;
import com.epam.auction.page.Page;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.page.PageName;
import com.epam.auction.page.TransferMethod;
import com.epam.auction.receiver.Receiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignInCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger();
    private static final String OPEN_SIGN_IN_ATTR = "openSignIn";

    public SignInCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public Page execute(RequestContent requestContent) {
        Page page = new Page();

        try {
            if (doAction(requestContent)) {
                page.setPageName(PageName.MAIN);
                page.setTransferMethod(TransferMethod.REDIRECT);
            } else {
                requestContent.setRequestAttribute(OPEN_SIGN_IN_ATTR, true);
                page.setPageName(PageName.MAIN);
                page.setTransferMethod(TransferMethod.FORWARD);
            }
        } catch (ReceiverLayerException e) {
            logger.log(Level.ERROR, e.getMessage(), e);
        }

        return page;
    }

}