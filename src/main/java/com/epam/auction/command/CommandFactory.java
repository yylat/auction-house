package com.epam.auction.command;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.receiver.RequestConstant;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Provides service to initialize command.
 */
public class CommandFactory {

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Creates command{@link AbstractCommand} from request parameter.
     *
     * @param requestContent request content
     * @return command
     */
    public AbstractCommand initCommand(RequestContent requestContent) {
        AbstractCommand command = CommandType.LOAD_ACTIVE_ITEMS.getCommand();
        String[] commandsNames = null;
        String commandName = null;
        try {
            commandsNames = requestContent.getRequestParameter(RequestConstant.COMMAND);
            if (commandsNames != null) {
                commandName = requestContent.getRequestParameter(RequestConstant.COMMAND)[0];
                CommandType commandType = CommandType.valueOf(commandName.toUpperCase().replaceAll("-", "_"));
                command = commandType.getCommand();
            }
        } catch (IllegalArgumentException e) {
            if (commandsNames == null || commandName == null) {
                LOGGER.log(Level.WARN, "No command attribute in request.");
            } else {
                LOGGER.log(Level.WARN, "Unknown command attribute [" + commandName + "] in request.");
            }
        }
        return command;
    }

}