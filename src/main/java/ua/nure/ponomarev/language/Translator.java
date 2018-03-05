package ua.nure.ponomarev.language;

/**
 * @author Bogdan_Ponamarev.
 */
public interface Translator {
    String translate(String text,String fromLang,String toLang);

    String translate(String text,String toLang);
}
