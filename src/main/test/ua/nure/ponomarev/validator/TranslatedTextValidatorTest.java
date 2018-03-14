package ua.nure.ponomarev.validator;

import com.google.gson.Gson;
import org.junit.Test;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.language.LanguageDetector;
import ua.nure.ponomarev.language.impl.YandexLanguageDetectorImpl;

/**
 * @author Bogdan_Ponamarev.
 */
public class TranslatedTextValidatorTest {
    private static final String separator = System.getProperty("line.separator");
    private Gson gson = new Gson();

    private LanguageDetector languageDetector = new YandexLanguageDetectorImpl(gson);

    private Validator<String> validator = new TranslatedTextValidator(languageDetector);

    @Test
    public void VALIDATE_CORRECT_TEST() throws ValidationException {
        validator.validate("hello = Привет" + separator
                + "How are you = как ты" + separator
                + "You are good = ты хорош" + separator);
    }

    @Test(expected = ValidationException.class)
    public void VALIDATE_INCORRECT_CAUSE_EMPTY() throws ValidationException {
        validator.validate("");
    }

    @Test(expected = ValidationException.class)
    public void VALIDATE_INCORRECT_CAUSE_UNSUPPORTED_LANGUAGE() throws ValidationException {
        validator.validate("Яскарво бачити тебе = приятно тебя видеть"+separator
        + "Тоді йдіть = тогда идите"+ separator);
    }

    @Test(expected = ValidationException.class)
    public void VALIDATE_INCORRECT_CAUSE_INCORRECT_PATTERN() throws ValidationException {
     validator.validate("Hello - я тут"+ separator
     + "How are you - как ты");
    }
}
