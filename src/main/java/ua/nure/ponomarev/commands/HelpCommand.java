package ua.nure.ponomarev.commands;

/**
 * @author Bogdan_Ponamarev.
 */
public class HelpCommand implements Command {
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");
    private static final String ANSWER_TEXT = "ATTENTION: If there is a symbol \"[\" in the template - you must write it"
            +LINE_SEPARATOR+"Commands:"
            + LINE_SEPARATOR + "/topics - get a list of topics that I currently have"
             + LINE_SEPARATOR + "/words [Name of topic] - get words by topic"
            +LINE_SEPARATOR + "Example:/words [Stages in life]"
            +LINE_SEPARATOR +"/translate [to which language you want translate] [Text to translate in ru or en] - translate text"
            +LINE_SEPARATOR+ "Example:/translate [ru] [Hello , how are you?]"
            +LINE_SEPARATOR + "/supported languages - get list of supported languages for translating"
            +LINE_SEPARATOR + "Example:/supportedLangs";
    @Override
    public String executeCommand(String fullCommand) {
        if(!fullCommand.equals("/help")){
            return ANSWER_FOR_INCORRECT_ASKING;
        }
        return ANSWER_TEXT;
    }
}
