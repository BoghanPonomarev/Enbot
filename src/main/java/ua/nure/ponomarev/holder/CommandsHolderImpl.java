package ua.nure.ponomarev.holder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.commands.Command;
import ua.nure.ponomarev.commands.StartCommand;
import ua.nure.ponomarev.exception.NoSuchCommandException;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Bogdan_Ponamarev.
 */
public class CommandsHolderImpl implements CommandsHolder {
    private static final Logger logger = LogManager.getLogger(CommandsHolderImpl.class);

    private  Map<String, Command> commands;

    public CommandsHolderImpl(Map<String, Command> commands) {
    this.commands =commands;
    }

    @Override
    public Command getCommand(String requestLine) throws NoSuchCommandException {
        String command = getCommandFromRequest(requestLine);
        if (!commands.containsKey(command)) {
            logger.info("No such command");
            throw new NoSuchCommandException("No such command");
        }
        return commands.get(command);
    }

    private String getCommandFromRequest(String requestLine) throws NoSuchCommandException {
        if (requestLine == null || requestLine.equals("")) {
            logger.info("Empty command");
            throw new NoSuchCommandException("Empty command");
        }
        if (requestLine.charAt(0) != '/') {
            logger.info("Command without slash");
            throw new NoSuchCommandException("Command without slash");
        }
        return (requestLine.indexOf(' ') == -1) ? requestLine.substring(1)
                : requestLine.substring(1, requestLine.indexOf(' '));
    }
}
