package ua.nure.ponomarev.words.reader;


import ua.nure.ponomarev.exceptions.InvalidTextException;

import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class FileWordReader implements WordReader {
    private static final Pattern linePattern = Pattern.compile("(\\w+) ?- ?(\\w+)");

    public String readWords(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("utf-8")));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine()).append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }

    /**
     * Method for validation of imputed words, if text does`nt match {@code linePattern}
     * throws exception
     *
     * @param text text that was obtained from user input
     * @throws InvalidTextException if @param text dos`nt math to @linePattern
     */
    private void validateText(String text) throws InvalidTextException {
       /* String[] lines = text.split(System.lineSeparator());
        for (String line : lines) {
            Matcher matcher = linePattern.matcher(line);
            *//*if(!matcher.find()||langof(matcher.group(1))){
                throw new InvalidTextException("Text dos`nt math");
            }*//*
        }*/
    }

}
