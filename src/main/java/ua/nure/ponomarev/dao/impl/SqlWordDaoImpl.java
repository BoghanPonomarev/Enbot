package ua.nure.ponomarev.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.SqlDaoConnectionManager;
import ua.nure.ponomarev.dao.WordDao;
import ua.nure.ponomarev.entity.Word;
import ua.nure.ponomarev.exception.DbException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
public class SqlWordDaoImpl implements WordDao {
    private static final Logger logger = LogManager.getLogger(SqlWordDaoImpl.class);
    public static final String SQL_SELECT_WORDS_BY_TOPIC_QUERY = "SELECT * FROM words INNER JOIN topics" +
            " ON words.topic_id = topics.id WHERE topics.name=?";
    private SqlDaoConnectionManager connectionManager;
    private static final String SQL_INSERT_WORD_QUERY = "INSERT INTO words(topic_id,word_en,word_ru) VALUES(?,?,?)";
    private static final String SQL_CHECK_IS_EXIST_WORD = "SELECT * FROM words WHERE word_en=? and word_ru=?";
    private static final String SQL_GET_TOPIC_ID_QUERY = "SELECT id FROM topics WHERE name=?";
    private static final String SQL_CREATE_TOPIC_QUERY = "INSERT INTO topics(name) VALUES(?)";

    public SqlWordDaoImpl(SqlDaoConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void insertWords(Word[] words, String topicName) throws DbException {
        PreparedStatement preparedStatement = null;
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_INSERT_WORD_QUERY);
            int topicId = getTopicId(topicName, connection);
            if (topicId == -1) {
                topicId = getTopicId(topicName, connection);
            }
            for (Word word : words) {
                if (!checkIsExistWordsColumn(word, connection)) {
                    preparedStatement.setInt(1, topicId);
                    preparedStatement.setString(2, word.getEnWord());
                    preparedStatement.setString(3, word.getRuWord());
                    preparedStatement.addBatch();
                }
            }
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            logger.error("There is some problems with SQL data base", e);
            throw new DbException("There is some problems with SQL data base", e);
        } finally {
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }

    private boolean checkIsExistWordsColumn(Word word, Connection connection) throws SQLException, DbException {
        PreparedStatement checkIsExistWordStatement = null;
        ResultSet resultSet = null;
        try {
            checkIsExistWordStatement = connection.prepareStatement(SQL_CHECK_IS_EXIST_WORD);
            checkIsExistWordStatement.setString(1, word.getEnWord());
            checkIsExistWordStatement.setString(2, word.getRuWord());
            resultSet = checkIsExistWordStatement.executeQuery();
            return resultSet.next();
        } finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(checkIsExistWordStatement);
        }
    }

    private int getTopicId(String topicName, Connection connection) throws SQLException, DbException {
        PreparedStatement preparedStatementToGet = null;
        ResultSet topicId = null;
        try {
            preparedStatementToGet = connection.prepareStatement(SQL_GET_TOPIC_ID_QUERY);
            preparedStatementToGet.setString(1, topicName);
            topicId = preparedStatementToGet.executeQuery();
            if (topicId.next()) {
                return topicId.getInt(1);
            }
            return -1;
        } finally {
            connectionManager.closeResultSet(topicId);
            connectionManager.closePrepareStatement(preparedStatementToGet);
        }
    }

    private int createNewTopic(String topicName, Connection connection) throws DbException {
        PreparedStatement preparedStatementToPut = null;
        ResultSet generatedTopicId = null;
        try {
            preparedStatementToPut = connection.prepareStatement(SQL_CREATE_TOPIC_QUERY
                    , PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatementToPut.setString(1, topicName);
            preparedStatementToPut.execute();
            generatedTopicId = preparedStatementToPut.getGeneratedKeys();
            generatedTopicId.next();
            return generatedTopicId.getInt(1);
        } catch (SQLException e) {
            logger.error("Can`t create new topic", e);
            throw new DbException("Can`t create new topic", e);
        } finally {
            connectionManager.closeResultSet(generatedTopicId);
            connectionManager.closePrepareStatement(preparedStatementToPut);
        }
    }

    public List<Word> getWordsByTopic(String topicName) throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Word> resultWords = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            if (getTopicId(topicName, connection) == -1) {
                return null;
            }
            preparedStatement = connection.prepareStatement(SQL_SELECT_WORDS_BY_TOPIC_QUERY);
            preparedStatement.setString(1, topicName);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Word tmpWord = new Word();
                tmpWord.setEnWord(resultSet.getString("words.word_en"));
                tmpWord.setRuWord(resultSet.getString("words.word_ru"));
                tmpWord.setTopic(resultSet.getString("topics.name"));
                resultWords.add(tmpWord);
            }
        } catch (SQLException e) {
            logger.error("Can`t get words by topic from sql data base", e);
            throw new DbException("Can`t get words by topic from sql data base", e);
        }
        return resultWords;
    }
}
