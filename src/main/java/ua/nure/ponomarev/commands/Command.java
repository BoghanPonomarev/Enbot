package ua.nure.ponomarev.commands;

/**
 * @author Bogdan_Ponamarev.
 */
public interface Command {
     String DATA_BASE_PROBLEM_ANSWER="Sorry,but now I have some problem with data holder,so I can`t answer";
    String ANSWER_FOR_INCORRECT_ASKING = "Seems that you did mistake in your request";
    String executeCommand(String fullCommand);
}
