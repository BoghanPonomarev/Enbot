package ua.nure.ponomarev.holder;

import ua.nure.ponomarev.commands.Command;
import ua.nure.ponomarev.exception.NoSuchCommandException;

/**
 * @author Bogdan_Ponamarev.
 */
public interface CommandsHolder {

    Command getCommand(String requestLine) throws NoSuchCommandException;
}
