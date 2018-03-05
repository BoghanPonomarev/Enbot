package ua.nure.ponomarev.bot;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public class BotGreeting {
    private List<String> greetingList;
    public BotGreeting(){
        greetingList = Arrays.asList(
                "Hi there!",
                "Hello, how are you?",
                "Welcome!",
                "Wow did not expect to see you here!",
                "Oh, you're very nice, I think we're friends!",
                "Lol, what have you forgotten here?",
                "Hello, good to see you!",
                "Hi,you looks great"
        );
    }

    public String getGreeting(){
        return greetingList.get((int)(Math.random()
                *(greetingList.size()-1)));
    }
}
