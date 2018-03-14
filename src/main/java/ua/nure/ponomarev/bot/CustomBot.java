package ua.nure.ponomarev.bot;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.telegram.telegrambots.api.methods.send.SendAudio;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import ua.nure.ponomarev.commands.Command;
import ua.nure.ponomarev.commands.TranslateCommand;
import ua.nure.ponomarev.exception.NoSuchCommandException;
import ua.nure.ponomarev.holder.CommandsHolder;
import ua.nure.ponomarev.speaker.TextSpeaker;
import ua.nure.ponomarev.speaker.TextSpeakerImpl;

import java.io.File;

/**
 * @author Bogdan_Ponamarev.
 */
public class CustomBot extends TelegramLongPollingBot{
    private static final Logger logger = LogManager.getLogger(CustomBot.class);

    private static final String BOT_NAME = "Mr_Stuart_bot";
    private static final String BOT_TOKEN = "550389121:AAFHw1xBkO-uEjZtvZrKg0cX1ZMPJlB0_PU";
    private CommandsHolder commandsHolder;
    private TextSpeaker textSpeaker = new TextSpeakerImpl();//HOW AVOID THIS LITTLE NONSENSE?
    public String getBotUsername() {
        return BOT_NAME;
    }

    public CustomBot(CommandsHolder commandsHolder){
        this.commandsHolder = commandsHolder;
    }

    /**
     * Method that reacts on user activities
     * @param request credential of that filled by bot api
     */
    public void onUpdateReceived(Update request) {
        Message msg = request.getMessage();
        String txt = msg.getText();
        try {
            Command command = commandsHolder.getCommand(txt);
            String result = command.executeCommand(txt);
            if(command instanceof TranslateCommand&&((TranslateCommand)command).getLanguage(txt).equals("en")){
                SendAudio sendAudio = new SendAudio();
                sendAudio.setChatId(msg.getChatId());
                sendAudio.setNewAudio(getAudio(result));
                sendAudio.setTitle("Kevin");
                sendAudio.setDuration(2);
                sendAudio(sendAudio);
                sendMsg(msg,result);
                return;
            }
            sendMsg(msg,result);
        } catch (NoSuchCommandException e1) {
           sendMsg(msg,"I do not understand");
        } catch (TelegramApiException e1) {
            logger.error("Can`t sand audio to user",e1);
        }
    }

    /**
     * Makes new file with voiced text
     * @param text Text to voice
     * @return new file with voiced text
     */
    private File getAudio(String text){
        String fullName = textSpeaker.voiceText(text);
        return new File(fullName);
    }

    /**
     * Send massage on telegram server
     * @param msg - entity filed by api
     * @param text massage text
     */
    @SuppressWarnings("deprecation")
    private void sendMsg(Message msg, String text) {
        SendMessage s = new SendMessage();
        s.setChatId(msg.getChatId());
        s.setText(text);
        try {
            sendMessage(s);
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    /**
     * @return token for telegram bot
     */
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

}

