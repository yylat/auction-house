package com.epam.auction.command;

import com.epam.auction.controller.PageGuide;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.Receiver;

public abstract class AbstractCommand {

    private Receiver receiver;

    public AbstractCommand(Receiver receiver) {
        this.receiver = receiver;
    }

    public Receiver getReceiver() {
        return receiver;
    }

    public abstract PageGuide execute(RequestContent requestContent);

    protected void doAction(RequestContent requestContent) throws ReceiverLayerException {
        receiver.action(CommandType.takeCommandType(this), requestContent);
    }

}