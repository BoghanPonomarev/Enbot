package ua.nure.ponomarev.commands;

/**
 * @author Bogdan_Ponamarev.
 */
public class HelpCommand implements Command {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String ANSWER_TEXT = "Commands:"+ LINE_SEPARATOR + "/topics - get a list of topics that I currently have"
             + LINE_SEPARATOR + "/words [Name of topic]";
    @Override
    public String executeCommand(String fullCommand) {
        if(!fullCommand.equals("/help")){
            return ANSWER_FOR_INCORRECT_ASKING;
        }
        return ANSWER_TEXT;
    }
}
