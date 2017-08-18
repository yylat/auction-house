package com.epam.auction.command.user;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.content.RequestContent;
import com.epam.auction.page.Page;
import com.epam.auction.page.TransferMethod;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.page.PageName;
import com.epam.auction.receiver.Receiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogOutCommand extends AbstractCommand {

    private static final Logger logger = LogManager.getLogger();

    public LogOutCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public Page execute(RequestContent requestContent) {

        try {
            doAction(requestContent);
        } catch (ReceiverLayerException e) {
            logger.log(Level.ERROR, e);
        }

        return new Page(PageName.MAIN, TransferMethod.REDIRECT);
    }

}