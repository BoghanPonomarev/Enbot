package ua.nure.ponomarev.validator;

import ua.nure.ponomarev.language.LanguageDetector;

import ua.nure.ponomarev.exception.ValidationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class TranslatedTextValidator implements Validator<String> {
    private static final Pattern linePattern = Pattern.compile("(\\w+) ?- ?(\\w+)");
    private LanguageDetector languageDetector;

    public  TranslatedTextValidator(LanguageDetector languageDetector){
        this.languageDetector = languageDetector;
    }
    /**
     * Method for validation of imputed words, if text does`nt match {@code linePattern}
     * throws exception
     *
     * @param parameter text that was obtained from user input
     * @throws ValidationException if @param text dos`nt math to @linePattern
     */
    public void validate(String parameter) throws ValidationException {
        String[] lines = parameter.split(System.getProperty("line.separator"));
        ValidationException exception = new ValidationException();
        for (String line : lines) {
            Matcher matcher = linePattern.matcher(line);
            if (!matcher.find()){
                exception.addMassage("Line \""+matcher.group() + "\" dose`nt match to pattern");
            }else if(!languageDetector.detectLang(matcher.group(1)).equals("en")){
                exception.addMassage("Word \""+matcher.group(1) + "\" must be in en");
            }else if(!languageDetector.detectLang(matcher.group(2)).equals("ru")) {
                exception.addMassage("Word \""+matcher.group(2) + "\" must be in ru");
            }
            if(!exception.isEmpty()){
                throw exception;
            }
        }
    }
}
