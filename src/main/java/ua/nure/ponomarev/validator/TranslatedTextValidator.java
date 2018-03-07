package ua.nure.ponomarev.validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.language.LanguageDetector;

import ua.nure.ponomarev.exception.ValidationException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class TranslatedTextValidator implements Validator<String> {
    private static final Logger logger = LogManager.getLogger(TranslatedTextValidator.class);
    private static final Pattern linePattern = Pattern.compile("([\\w -`()]+)=([\\w -`()]+)", Pattern.UNICODE_CHARACTER_CLASS);
    private LanguageDetector languageDetector;


    public  TranslatedTextValidator(LanguageDetector languageDetector){
        this.languageDetector = languageDetector;
    }
    /**
     * Method for validation of imputed words, if text does`nt match {@code linePattern}
     * throws exception
     * File with words must be in strict format -
     * ("Topic:name of topic"
     * "english word - russian translate"
     * "another english word - another translate")
     * @param parameter text that was obtained from user input
     * @throws ValidationException if @param text dos`nt math to @linePattern
     */
    public void validate(String parameter) throws ValidationException {
        String[] lines = parameter.split(System.getProperty("line.separator"));

        ValidationException exception = new ValidationException();
        for (int i=0;i<lines.length;i++) {
            Matcher matcher = linePattern.matcher(lines[i]);
            if (!matcher.find()){
                exception.addMassage("Line "+i+ " dose`nt match to pattern");
                logger.info("Line "+i+ " dose`nt match to pattern");
                break;
            }else if(!languageDetector.detectLang(matcher.group(1)).equals("en")){
                exception.addMassage("Word \""+matcher.group(1) + "\" must be in en");
                logger.info("Word \""+matcher.group(1) + "\" must be in en");
                break;
            }else if(!languageDetector.detectLang(matcher.group(2)).equals("ru")) {
                exception.addMassage("Word \""+matcher.group(2) + "\" must be in ru");
                logger.info("Word \""+matcher.group(2) + "\" must be in ru");
                break;
            }
        }
        if(!exception.isEmpty()){

            throw exception;
        }
    }
}
