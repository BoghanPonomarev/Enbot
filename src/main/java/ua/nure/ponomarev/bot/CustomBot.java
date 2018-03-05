package ua.nure.ponomarev.bot; /**
 * @author Bogdan_Ponamarev.
 */


import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class CustomBot extends TelegramLongPollingBot{

    private static final String BOT_NAME = "Mr_Stuart_bot";
    private static final String BOT_TOKEN = "550389121:AAFHw1xBkO-uEjZtvZrKg0cX1ZMPJlB0_PU";
    private BotGreeting botGreeting;

    public CustomBot(BotGreeting botGreeting){
        this.botGreeting = botGreeting;
    }
    public String getBotUsername() {
        return BOT_NAME;
    }


    public void onUpdateReceived(Update e) {
        Message msg = e.getMessage(); // Это нам понадобится
        String txt = msg.getText();
        if (txt.equals("/start")) {
            sendMsg(msg, botGreeting.getGreeting());
        }
        if(txt.equals("/name")){
            sendMsg(msg,"Hi");
        }
    }

    @SuppressWarnings("deprecation") // Означает то, что в новых версиях метод уберут или заменят
    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId()); // Боту может писать не один человек, и поэтому чтобы отправить сообщение, грубо говоря нужно узнать куда его отправлять
        s.setText(text);
        try { //Чтобы не крашнулась программа при вылете Exception
            sendMessage(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}

