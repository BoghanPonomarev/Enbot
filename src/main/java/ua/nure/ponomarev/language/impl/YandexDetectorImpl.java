package ua.nure.ponomarev.language.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.language.Detector;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * @author Bogdan_Ponamarev.
 */
public class YandexDetectorImpl implements Detector {
    private static final Logger logger = LogManager.getLogger(YandexDetectorImpl.class);
    private static final String YANDEX_KEY = "trnsl.1.1.20180304T223522Z.42a08a8ffd4cee07.4c6362f5feb6bc0811884084f3f2894f7c27f791";
    private static final String HTTP_YANDEX_REQUEST_FOR_DETECT = "https://translate.yandex.net/api/v1.5/tr.json/detect?key=" + YANDEX_KEY + "&text=";
    private Gson gson;

    public YandexDetectorImpl(Gson gson) {
        this.gson = gson;
    }

    /**
     * Detect language by text
     * @param text parameter by which method defines what language is it
     * @return language code in two letters format
     */
    public String detectLang(String text) {
        try {
            String response = sendRequest(HTTP_YANDEX_REQUEST_FOR_DETECT
                    + URLEncoder.encode(text, "UTF-8"));
            YandexResponseJson result = gson.fromJson(response, YandexResponseJson.class);
            return (result != null) ? result.getLang() : null;
        } catch (UnsupportedEncodingException e) {
            logger.error("Encoding is invalid");
        }
        return null;
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

            InputStream in = uc.getInputStream();
            int ch;
            StringBuilder text = new StringBuilder();

            while ((ch = in.read()) != -1) {
                text.append((char) ch);
            }
            logger.debug("Server responded:\n" + text + "\n");
            return text.toString();
        } catch (Exception e) {
            logger.error("Something wrong with url connection to yandex detection");
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
        private String lang;
    }
}
