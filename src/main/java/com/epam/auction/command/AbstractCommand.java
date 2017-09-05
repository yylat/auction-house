package com.epam.auction.command;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.Receiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    private Receiver receiver;

    public AbstractCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public abstract PageGuide execute(RequestContent requestContent);

    protected void doAction(RequestContent requestContent) throws ReceiverException {
        receiver.action(CommandType.takeCommandType(this), requestContent);
    }

    protected void handleReceiverException(PageGuide pageGuide, ReceiverException e) {
        LOGGER.log(Level.ERROR, e);
        pageGuide.setPageAddress(PageAddress.ERROR);
        pageGuide.setTransferMethod(TransferMethod.REDIRECT);
    }

}