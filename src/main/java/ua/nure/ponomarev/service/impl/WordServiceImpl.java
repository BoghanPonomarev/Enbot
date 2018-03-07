package ua.nure.ponomarev.service.impl;

import lombok.AllArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.WordDao;
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
public class WordServiceImpl implements WordService {
    private static final Logger logger = LogManager.getLogger(WordServiceImpl.class);

    private TransactionManager transactionManager;

    private LanguageDetector languageDetector;

    private Translator translator;

    private Validator<String> inputedWordsValidator;

    private WordTextParser wordTextParser;

    private WordDao wordDao;

    @Override
    public void insertWords(String text,String topicName) throws DbException, ValidationException {
        transactionManager.doWithTransaction(()->{
            inputedWordsValidator.validate(text);
            wordDao.insertWords(wordTextParser.parse(text),topicName);
            return null;
        });
    }

    @Override
    public List<Word> getWordsByTopic(String topic) throws DbException, ValidationException {
        return transactionManager.doWithTransaction(()-> wordDao.getWordsByTopic(topic));
    }
}
