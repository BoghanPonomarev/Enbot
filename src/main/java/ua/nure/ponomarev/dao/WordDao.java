package ua.nure.ponomarev.dao;

import ua.nure.ponomarev.entity.Word;
import ua.nure.ponomarev.exception.DbException;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public interface WordDao {
     void insertWords(Word[] words, String topicName) throws DbException;

     List<Word> getWordsByTopic(String topic) throws DbException;
    }
