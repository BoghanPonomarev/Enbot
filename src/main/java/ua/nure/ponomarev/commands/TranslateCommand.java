package ua.nure.ponomarev.commands;

/**
 * @author Bogdan_Ponamarev.
 */
public interface TranslateCommand extends Command {
    @Override
    String executeCommand(String fullCommand) ;

     String getLanguage(String fullCommand);

    }
