package ua.nure.ponomarev.words.reader;



import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.validator.Validator;

import java.io.*;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Bogdan_Ponamarev.
 */
public class FileWordReader implements WordReader {
    private Validator<String> validator;
    public FileWordReader(Validator<String> validator){
        this.validator = validator;
    }

    public String readWords(InputStream inputStream) throws IOException, ValidationException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("utf-8")));
        StringBuilder stringBuilder = new StringBuilder();
        while (bufferedReader.ready()) {
            stringBuilder.append(bufferedReader.readLine()).append(System.getProperty("line.separator"));
        }
        validator.validate(stringBuilder.toString());
        return stringBuilder.toString();
    }


}
