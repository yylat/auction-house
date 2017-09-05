package com.epam.auction.command;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.Receiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides base functionality for command classes.
 */
public abstract class AbstractCommand {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Receiver of command.
     */
    private Receiver receiver;

    /**
     * Constructs command with receiver.
     *
     * @param receiver receiver
     */
    public AbstractCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Returns receiver of the command.
     *
     * @return receiver of the command
     */
    public Receiver getReceiver() {
        return receiver;
    }

    /**
     * Executes command on request content.
     *
     * @param requestContent request content
     * @return page to go
     */
    public abstract PageGuide execute(RequestContent requestContent);

    /**
     * Does action on request content according to command type and receiver.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not do action
     */
    protected void doAction(RequestContent requestContent) throws ReceiverException {
        receiver.action(CommandType.takeCommandType(this), requestContent);
    }

    /**
     * Handles {@link ReceiverException}.
     *
     * @param pageGuide page to go
     * @param e         exception
     */
    protected void handleReceiverException(PageGuide pageGuide, ReceiverException e) {
        LOGGER.log(Level.ERROR, e);
        pageGuide.setPageAddress(PageAddress.ERROR);
        pageGuide.setTransferMethod(TransferMethod.REDIRECT);
    }

}