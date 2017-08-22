package com.epam.auction.command.user;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.command.RequestContent;
import com.epam.auction.controller.PageAddress;
import com.epam.auction.controller.PageGuide;
import com.epam.auction.controller.TransferMethod;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.Receiver;
import com.epam.auction.receiver.RequestConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SignInCommand extends AbstractCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    public SignInCommand(Receiver receiver) {
        super(receiver);
    }

    @Override
    public PageGuide execute(RequestContent requestContent) {
        PageGuide pageGuide = new PageGuide(PageAddress.ACTIVE_ITEMS);

        try {
            doAction(requestContent);
            if (requestContent.getSessionAttribute(RequestConstant.USER) != null) {
                pageGuide.setTransferMethod(TransferMethod.REDIRECT);
            } else {
                pageGuide.setTransferMethod(TransferMethod.FORWARD);
            }
        } catch (ReceiverLayerException e) {
            LOGGER.log(Level.ERROR, e.getMessage(), e);
        }

        return pageGuide;
    }

}