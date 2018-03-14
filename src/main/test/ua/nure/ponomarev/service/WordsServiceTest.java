package ua.nure.ponomarev.service;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.nure.ponomarev.entity.Word;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.impl.SqlWordDaoImpl;
import ua.nure.ponomarev.language.LanguageDetector;
import ua.nure.ponomarev.language.Translator;
import ua.nure.ponomarev.language.impl.YandexTranslatorImpl;
import ua.nure.ponomarev.parser.WordTextParser;
import ua.nure.ponomarev.service.impl.WordServiceImpl;
import ua.nure.ponomarev.transaction.TransactionManager;
import ua.nure.ponomarev.validator.TranslatedTextValidator;
import ua.nure.ponomarev.validator.Validator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

/**
 * @author Bogdan_Ponamarev.
 */
@RunWith(MockitoJUnitRunner.class)
public class WordsServiceTest {
    @Mock
    private DataSource dataSource;

    @Mock
    private SqlWordDaoImpl wordDao;

    @Mock
    private Validator<String> inputedWordsValidator = new TranslatedTextValidator(mock(LanguageDetector.class));

    @Mock
    private WordTextParser wordTextParser;

    @InjectMocks
    private TransactionManager transactionManager = new TransactionManager(dataSource);
    private Translator translator = new YandexTranslatorImpl(new Gson());

    @InjectMocks
    private WordServiceImpl wordService = new WordServiceImpl(transactionManager, translator, inputedWordsValidator,
            wordTextParser, wordDao);

    @Before
    public void before() throws SQLException {
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
    }

    @Test
    public void INSERT_CORRECT_WORDS_TEST() throws DbException, ValidationException {
        String input = "Hello = Привет" + System.getProperty("line.separator") + "How are you? = Как ты?";
        Word[] inputedWordsArr = new Word[3];
        String topicName = "Topic";
        when(wordTextParser.parse(input)).thenReturn(inputedWordsArr);
        wordService.insertWords(input, topicName);
        verify(wordDao).insertWords(inputedWordsArr, topicName);
    }

    @Test(expected = ValidationException.class)
    public void INSERT_INCORRECT_WORDS_TEST() throws DbException, ValidationException {
        String input = "Hello = Привет" + System.getProperty("line.separator") + "How are you? = Как ты?";
        String topicName = "Topic";
        when(wordTextParser.parse(input)).thenThrow(ValidationException.class);
        wordService.insertWords(input, topicName);
    }

    @Test
    public void GET_WORDS_BY_TOPIC_FELT() throws DbException, ValidationException {
        String topicName = "Topic";
        List<Word> expected = new ArrayList<>();
        expected.add(new Word(topicName, "ru", "en"));
        when(wordDao.getWordsByTopic(topicName)).thenReturn(expected);
        Assert.assertEquals(expected, wordService.getWordsByTopic(topicName));
    }

    @Test
    public void GET_WORDS_BY_TOPIC_EMPTY() throws DbException, ValidationException {
        String topicName = "Topic";
        List<Word> expected = new ArrayList<>();
        when(wordDao.getWordsByTopic(topicName)).thenReturn(expected);
        Assert.assertEquals(expected, wordService.getWordsByTopic(topicName));
    }

    @Test
    public void TRANSLATE_TEST() {
    String textToTranslate = "Hello, I am sure that you listen me";
    String expected = "Здравствуйте, я уверен, что вы слушаете меня";
    Assert.assertEquals(expected,wordService.translate(textToTranslate,"ru"));
    }

    @Test
    public void TRANSLATE_EMTY_TEST() {
        String textToTranslate = "";
        Assert.assertEquals(null,wordService.translate(textToTranslate,"ru"));
    }

    @Test
    public void TRANSLATE_WITH_WRONG_LANGUAGE_TEST() {
        String textToTranslate = "Hello, I am sure that you listen me";
        Assert.assertEquals(null,wordService.translate(textToTranslate,"oo"));
    }

}
