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
public class TranslateCommandImpl implements TranslateCommand {
    private static final Logger logger = LogManager.getLogger(TranslateCommandImpl.class);
    private static final Pattern requestPattern = Pattern.compile("/translate \\[\\w{2}\\] ?\\[[\\w -`:?!$]+\\]"
            ,Pattern.UNICODE_CHARACTER_CLASS);
    private WordService wordService;

    @Override
    public String executeCommand(String fullCommand) {
        if(!isValidRequest(fullCommand)){
            return ANSWER_FOR_INCORRECT_ASKING;
        }
     return wordService.translate(getTextFromRequest(fullCommand),getLanguage(fullCommand));
    }

    private boolean isValidRequest(String fullCommand){
        return requestPattern.matcher(fullCommand).matches();

    }

    public String getLanguage(String fullCommand){
        return fullCommand.substring(fullCommand.indexOf('[')+1,fullCommand.indexOf(']'));
    }

    private String getTextFromRequest(String fullCommand){
        return fullCommand.substring(fullCommand.indexOf('[',16)+1,fullCommand.lastIndexOf(']'));
    }

}
