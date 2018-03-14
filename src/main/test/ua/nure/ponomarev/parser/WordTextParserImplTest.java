package ua.nure.ponomarev.parser;

import org.junit.Assert;
import org.junit.Test;
import ua.nure.ponomarev.entity.Word;

/**
 * @author Bogdan_Ponamarev.
 */
public class WordTextParserImplTest {
    private static final String separator = System.getProperty("line.separator");

    private WordTextParserImpl parser = new WordTextParserImpl();

    @Test
    public void VALID_PARSE(){
        String [] expected = {"hello", "привет","I am so old", "я очень стар" ,"And you too", "и ты тоже" };
        Word[] words = parser.parse("hello = привет" + separator +
         "I am so old = я очень стар" + separator +
        " And you too = и ты тоже"+ separator);
        for(int i=0,j=0;i<expected.length/2;i++){
            Assert.assertEquals(expected[i++],words[j].getEnWord());
            Assert.assertEquals(expected[i],words[j++].getRuWord());
        }
    }


}
