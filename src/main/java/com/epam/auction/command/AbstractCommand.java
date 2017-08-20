package com.epam.auction.command;

import com.epam.auction.content.RequestContent;
import com.epam.auction.page.Page;
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

    public abstract Page execute(RequestContent requestContent);

    protected void doAction(RequestContent requestContent) throws ReceiverLayerException {
        receiver.action(CommandType.takeCommandType(this), requestContent);
    }

}