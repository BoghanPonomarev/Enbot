package ua.nure.ponomarev.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.WordDao;
import ua.nure.ponomarev.entity.Word;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.language.LanguageDetector;
import ua.nure.ponomarev.language.Translator;
import ua.nure.ponomarev.parser.WordTextParser;
import ua.nure.ponomarev.service.WordService;
import ua.nure.ponomarev.transaction.TransactionManager;
import ua.nure.ponomarev.validator.Validator;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WordServiceImpl implements WordService {
    private static final Logger logger = LogManager.getLogger(WordServiceImpl.class);

    private TransactionManager transactionManager;

    private Translator translator;

    private Validator<String> inputedWordsValidator;

    private WordTextParser wordTextParser;

    private WordDao wordDao;

    /**
     * Insert words into data holder
     * @param text text that will be validated and parsed into words and after
     * in will be pushed in data holder
     * @param topicName name of new topic
     * @throws DbException if there is problem with data holder
     * @throws ValidationException if {@Code text} is not valid
     */
    @Override
    public void insertWords(String text, String topicName) throws DbException, ValidationException {
        transactionManager.doWithTransaction(() -> {
            inputedWordsValidator.validate(text);
            wordDao.insertWords(wordTextParser.parse(text), topicName);
            return null;
        });
    }

    /**
     * Get words by particular topic
     * @param topic words of which will be returned
     * @return List of words by {@code topic} or empty list
     * @throws DbException if there is problem with data holder
     */
    @Override
    public List<Word> getWordsByTopic(String topic) throws DbException, ValidationException {
        return transactionManager.doWithTransaction(() -> wordDao.getWordsByTopic(topic));
    }

    /**
     * Translate text to certain language
     * @param text - text to translate
     * @param toLanguage - language in which text will be translated, must be in two-letters format
     * @return translated text
     */
    @Override
    public String translate(String text, String toLanguage) {
        return translator.translate(text, toLanguage);
    }

    /**
     * Get list of supported languages to translation
     * @return list of supported languages in two-letters format
     */
    @Override
    public List<String> getSupportedLanguages() {
     return translator.getSupportedLanguages();
    }
}
