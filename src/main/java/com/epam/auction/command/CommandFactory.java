package com.epam.auction.command;

import com.epam.auction.receiver.RequestConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {

    private final static Logger LOGGER = LogManager.getLogger();

    public AbstractCommand initCommand(RequestContent requestContent) {
        AbstractCommand command;
        String commandName = null;
        try {
            commandName = requestContent.getRequestParameter(RequestConstant.COMMAND)[0];
            CommandType commandType = CommandType.valueOf(commandName.toUpperCase().replaceAll("-", "_"));
            command = commandType.getCommand();
        } catch (IllegalArgumentException e) {
            if (commandName == null) {
                LOGGER.log(Level.WARN, "No command attribute in request.");
            } else {
                LOGGER.log(Level.WARN, "Unknown command attribute [" + commandName + "] in request.");
            }
            command = CommandType.LOAD_ACTIVE_ITEMS.getCommand();
        }
        return command;
    }

}