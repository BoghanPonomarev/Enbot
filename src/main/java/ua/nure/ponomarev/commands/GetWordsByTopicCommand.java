package ua.nure.ponomarev.commands;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import ua.nure.ponomarev.entity.Word;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.service.WordService;

import java.util.regex.*;
/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class GetWordsByTopicCommand implements Command {
    private static final Logger logger = LogManager.getLogger(GetAllTopicsCommand.class);
    private static final String NO_TOPIC_ANSWER = "There is no such topic";

    private WordService wordService;
    @Override
    public String executeCommand(String fullCommand) {
        if(!isValidRequest(fullCommand)){
            return ANSWER_FOR_INCORRECT_ASKING;
        }
        try {
            String topic = getTopicFromRequest(fullCommand);
            StringBuilder stringBuilder = new StringBuilder(topic).append(": ")
                    .append(System.getProperty("line.separator"));
            List<Word> words = wordService.getWordsByTopic(topic);
            if(words==null){
                return NO_TOPIC_ANSWER;
            }
             words.forEach((Word word)->{
                stringBuilder.append(word.getEnWord()).append(" - ").append(word.getRuWord())
                        .append(System.getProperty("line.separator"));
            });
             return stringBuilder.toString();
        } catch (DbException e) {
            logger.error("Can`t give words to user cause problem with data base",e);
            return DATA_BASE_PROBLEM_ANSWER;
        } catch (ValidationException e) {
            return ANSWER_FOR_INCORRECT_ASKING;
        }
    }
    private boolean isValidRequest(String fullCommand){
        return fullCommand.matches("/words \\[[\\w -`]+\\]");
    }
    private String getTopicFromRequest(String fullCommand){
        return fullCommand.substring(fullCommand.indexOf('[')+1,fullCommand.lastIndexOf(']'));
    }
}
