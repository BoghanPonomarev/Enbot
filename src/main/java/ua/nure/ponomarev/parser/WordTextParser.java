package ua.nure.ponomarev.parser;

import ua.nure.ponomarev.entity.Word;

/**
 * @author Bogdan_Ponamarev.
 */
public interface WordTextParser {
    Word [] parse (String text);
}
