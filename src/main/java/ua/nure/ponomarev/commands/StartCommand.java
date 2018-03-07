package ua.nure.ponomarev.commands;

import java.util.Arrays;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class StartCommand implements Command {
    private static List<String> greetingList;
    private static final String DESCRIBING = "I'll help you with your English.I'm easy to use," +
            System.getProperty("line.separator")+"just follow the instructions written in the command /help.";
    public StartCommand(){
        greetingList = Arrays.asList(
                "Hi there!",
                "Hello, how are you?",
                "Welcome!",
                "Oh, you're very nice, I think we're friends!",
                "Hello, good to see you!",
                "Hi,you looks great"
        );
    }
    @Override
    public String executeCommand(String fullCommand) {
        if(!fullCommand.equals("/start")){
            return ANSWER_FOR_INCORRECT_ASKING;
        }
        return greetingList.get((int)(Math.random()
                *(greetingList.size()-1)))+System.getProperty("line.separator") + DESCRIBING;
    }


}
