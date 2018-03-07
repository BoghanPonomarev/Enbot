package ua.nure.ponomarev.commands;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.entity.Topic;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.service.TopicService;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class GetAllTopicsCommand implements Command {
    private static final Logger logger= LogManager.getLogger(GetAllTopicsCommand.class);
    private  static final String ANSWER_HEAD = "Current topics:";
    private TopicService topicService;


    @Override
    public String executeCommand(String fullCommand) {
        if(!fullCommand.equals("/topics")){
            return ANSWER_FOR_INCORRECT_ASKING;
        }
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(ANSWER_HEAD).append(System.getProperty("line.separator"));
            topicService.getAllTopics().stream().forEach((Topic topic)->{
                stringBuilder.append(topic.getName()).append(System.getProperty("line.separator"));
            });
            return stringBuilder.toString();
        } catch (DbException e) {
            logger.error("Can`t send all topics to user",e);
            return DATA_BASE_PROBLEM_ANSWER;
        } catch (ValidationException e) {
            return ANSWER_FOR_INCORRECT_ASKING;
        }
    }
}
