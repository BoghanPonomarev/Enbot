package ua.nure.ponomarev;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ua.nure.ponomarev.bot.BotGreeting;
import ua.nure.ponomarev.bot.CustomBot;

/**
 * @author Bogdan_Ponamarev.
 */
public class Demo {
    public static void main(String[] args) {
        ApiContextInitializer.init(); // Инициализируем апи
        TelegramBotsApi botApi = new TelegramBotsApi();
        try {
            botApi.registerBot(new CustomBot(new BotGreeting()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
