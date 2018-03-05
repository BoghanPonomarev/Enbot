package ua.nure.ponomarev.language.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.language.Translator;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * @author Bogdan_Ponamarev.
 */
public class YandexTranslatorImpl implements Translator {
    private static final Logger logger = LogManager.getLogger(YandexTranslatorImpl.class);
    private static final String YANDEX_KEY = "trnsl.1.1.20180304T223522Z.42a08a8ffd4cee07.4c6362f5feb6bc0811884084f3f2894f7c27f791";
    private static final String HTTP_YANDEX_REQUEST_FOR_TRANSLATE = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
    + YANDEX_KEY + "&text=%s&lang=%s-%s";
    private static final String HTTP_YANDEX_REQUEST_FOR_TRANSLATE_WITHOUT_FROM_LANGUAGE = "https://translate.yandex.net/api/v1.5/tr.json/translate?key="
            + YANDEX_KEY + "&text=%s&lang=%s";
    private static final String ENCODING = "UTF-8";
    private Gson gson;

    public YandexTranslatorImpl(Gson gson){
        this.gson = gson;
    }

    /**
     * Translate text from one language to another
     * @param text text for translating
     * @param fromLang language from which text must be translated
     * @param toLang language in which text must be translated
     * @return translated text
     */
    public String translate(String text, String fromLang, String toLang) {
        String response = null;
        try {
            response = sendRequest(String.format(HTTP_YANDEX_REQUEST_FOR_TRANSLATE, URLEncoder.encode(text,ENCODING),fromLang,toLang));
        } catch (UnsupportedEncodingException e) {
            logger.error("Encoding is invalid");
        }
        YandexResponseJson result = gson.fromJson(response, YandexResponseJson.class);
        return (result!=null) ? result.getText()[0] : null;
    }
    /**
     * Identify language from which text must be translated and translate
     * text from one language to another
     * @param text text for translating
     * @param toLang language in which text must be translated
     * @return translated text
     */
    public String translate(String text, String toLang) {
        String response = null;
        try {
            response = sendRequest(String.format(HTTP_YANDEX_REQUEST_FOR_TRANSLATE_WITHOUT_FROM_LANGUAGE, URLEncoder.encode(text,ENCODING),toLang));
        } catch (UnsupportedEncodingException e) {
            logger.error("Encoding is invalid");
        }
        YandexResponseJson result = gson.fromJson(response, YandexResponseJson.class);
        return (result!=null) ? result.getText()[0] : null;
    }

    /**
     * Send http request to yandex translator with filled parameters
     * @param URL_TO_SEND url of yandex translator
     * @return json format of code language
     */
    private String sendRequest(String URL_TO_SEND) {
        try {
            URL url = new URL(URL_TO_SEND);
            URLConnection uc = url.openConnection();
            InputStreamReader in = new InputStreamReader(uc.getInputStream(), Charset.forName(ENCODING));
            int ch;
            StringBuilder text = new StringBuilder();

            while ((ch = in.read()) != -1) {
                text.append((char) ch);
            }
            logger.debug("Server responded:\n" + text + "\n");
            return text.toString();
        } catch (Exception e) {
            logger.error("Something wrong with url connection to yandex translator");
        }
        return null;
    }

    /**
     * Entity class for JSON
     */
    @Getter
    @Setter
    @AllArgsConstructor
    private class YandexResponseJson {
        private String [] text;
    }
}
