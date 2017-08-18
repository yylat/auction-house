package com.epam.auction.factory;

import com.epam.auction.command.AbstractCommand;
import com.epam.auction.command.CommandType;
import com.epam.auction.constant.RequestConstant;
import com.epam.auction.content.RequestContent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CommandFactory {

    private final static Logger logger = LogManager.getLogger();

    public AbstractCommand initCommand(RequestContent requestContent) {
        AbstractCommand command;
        String commandName = null;
        try {
            commandName = requestContent.getRequestParameter(RequestConstant.COMMAND)[0];
            CommandType commandType = CommandType.valueOf(commandName.toUpperCase().replaceAll("-", "_"));
            command = commandType.getCommand();
        } catch (IllegalArgumentException e) {
            if (commandName == null) {
                logger.log(Level.WARN, "No command attribute in request.");
            } else {
                logger.log(Level.WARN, "Unknown command attribute [" + commandName + "] in request.");
            }
            command = CommandType.OPEN_MAIN_PAGE.getCommand();
        }
        return command;
    }

}