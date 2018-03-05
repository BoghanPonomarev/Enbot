package ua.nure.ponomarev.words.reader;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

/**
 * @author Bogdan_Ponamarev.
 */
public interface WordReader {
    String readWords(InputStream inputStream) throws IOException;
}
