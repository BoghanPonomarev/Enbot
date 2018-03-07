package ua.nure.ponomarev.service;

import ua.nure.ponomarev.entity.Word;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public interface WordService {

    void insertWords(String text,String topicName) throws DbException, ValidationException;

    List<Word> getWordsByTopic(String topic) throws DbException, ValidationException;
}
