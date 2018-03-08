package ua.nure.ponomarev.commands;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.service.WordService;
import java.util.regex.*;
/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class TranslateCommand implements Command {
    private static final Logger logger = LogManager.getLogger(TranslateCommand.class);
    private static final Pattern requestPattern = Pattern.compile("/translate \\[[\\w -`]+\\]"
            ,Pattern.UNICODE_CHARACTER_CLASS);
    private WordService wordService;

    @Override
    public String executeCommand(String fullCommand) {
        if(!isValidRequest(fullCommand)){
            return ANSWER_FOR_INCORRECT_ASKING;
        }
     return wordService.translate(getTextFromRequest(fullCommand));
    }

    private boolean isValidRequest(String fullCommand){
        return requestPattern.matcher(fullCommand).matches();

    }

    private String getTextFromRequest(String fullCommand){
        return fullCommand.substring(fullCommand.indexOf('[')+1,fullCommand.lastIndexOf(']'));
    }

}
