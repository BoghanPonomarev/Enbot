package ua.nure.ponomarev.parser;

import ua.nure.ponomarev.entity.Word;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author Bogdan_Ponamarev.
 */
public class WordTextParserImpl implements WordTextParser{
    /**
     * Parse en-ru words in array of entity objects, between this
     * parsing text must be validated
     * @param text text to parse
     * @return array of words
     */
    @Override
    public Word[] parse(String text) {
        String [] lines =text.split(System.getProperty("line.separator"));
        Word [] resultWords = new Word[lines.length];
        for (int i=0;i<lines.length;i++) {
            Word tmpWord = new Word();
            tmpWord.setEnWord(lines[i].split("=")[0].trim());
            tmpWord.setRuWord(lines[i].split("=")[1].trim());
            resultWords[i] = tmpWord;
        }
        return resultWords;
    }
}
