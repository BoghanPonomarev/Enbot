package ua.nure.ponomarev.commands;

import lombok.AllArgsConstructor;
import ua.nure.ponomarev.service.WordService;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
public class GetSupportedLanguagesCommand implements Command {
    private WordService wordService;
    @Override
    public String executeCommand(String fullCommand) {
        if(!fullCommand.equals("/supportedLangs")){
            return ANSWER_FOR_INCORRECT_ASKING;
        }
        StringBuilder stringBuilder = new StringBuilder();
        wordService.getSupportedLanguages().stream().forEach((String str)->{
            stringBuilder.append(str);
        });
        return stringBuilder.toString();
    }
}
