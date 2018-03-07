package ua.nure.ponomarev.web.listener;

import com.google.gson.Gson;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ua.nure.ponomarev.bot.CustomBot;
import ua.nure.ponomarev.commands.*;
import ua.nure.ponomarev.dao.SqlDaoConnectionManager;
import ua.nure.ponomarev.dao.impl.SqlTopicDaoImpl;
import ua.nure.ponomarev.dao.impl.SqlWordDaoImpl;
import ua.nure.ponomarev.holder.CommandsHolder;
import ua.nure.ponomarev.holder.CommandsHolderImpl;
import ua.nure.ponomarev.language.LanguageDetector;
import ua.nure.ponomarev.language.Translator;
import ua.nure.ponomarev.language.impl.YandexLanguageDetectorImpl;
import ua.nure.ponomarev.language.impl.YandexTranslatorImpl;
import ua.nure.ponomarev.parser.WordTextParserImpl;
import ua.nure.ponomarev.service.WordService;
import ua.nure.ponomarev.service.impl.TopicServiceImpl;
import ua.nure.ponomarev.service.impl.WordServiceImpl;
import ua.nure.ponomarev.transaction.TransactionManager;
import ua.nure.ponomarev.validator.TranslatedTextValidator;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
/**
 * @author Bogdan_Ponamarev.
 */
@WebListener
public class ServletListener implements ServletContextListener {
    @Resource(name = "jdbc/data_source")
    private DataSource dataSource;

    private SqlDaoConnectionManager connectionManager;

    private TransactionManager transactionManager;

    private LanguageDetector languageDetector;

    private Translator translator;

    private WordService wordService;
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext servletContext = sce.getServletContext();
        WordServiceInitializing(servletContext);
        botInitializing();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

    private void WordServiceInitializing(ServletContext servletContext){
        Gson gson = new Gson();
        languageDetector = new YandexLanguageDetectorImpl(gson);
        translator = new YandexTranslatorImpl(gson);
        connectionManager = new SqlDaoConnectionManager(dataSource);
        transactionManager = new TransactionManager(dataSource);
        wordService =new WordServiceImpl(transactionManager,languageDetector
                ,translator,new TranslatedTextValidator(languageDetector),
                new WordTextParserImpl(),new SqlWordDaoImpl(connectionManager));
        servletContext.setAttribute("word_service",wordService);

    }
    private CommandsHolder commandsInitializing(){
        Map<String,Command> commands = new HashMap<>();
        commands.put("start",new StartCommand());
        commands.put("help",new HelpCommand());
        commands.put("topics",new GetAllTopicsCommand(new TopicServiceImpl(transactionManager
                ,new SqlTopicDaoImpl(connectionManager))));
        commands.put("words",new GetWordsByTopicCommand(wordService));
        return new CommandsHolderImpl(commands);
    }
    private void botInitializing(){
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new CustomBot(commandsInitializing()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
