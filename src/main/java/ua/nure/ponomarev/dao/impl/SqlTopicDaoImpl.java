package ua.nure.ponomarev.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.SqlDaoConnectionManager;
import ua.nure.ponomarev.dao.TopicDao;
import ua.nure.ponomarev.entity.Topic;
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
public class SqlTopicDaoImpl implements TopicDao {
    private static final Logger logger = LogManager.getLogger(SqlTopicDaoImpl.class);
    public static final String SQL_SELECT_ALL_TOPICS_QUERY = "SELECT * FROM topics";
    private SqlDaoConnectionManager connectionManager;

    public SqlTopicDaoImpl(SqlDaoConnectionManager sqlDaoConnectionManager){
        connectionManager = sqlDaoConnectionManager;
    }
    @Override
    public List<Topic> getAllTopics() throws DbException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        List<Topic> resultList = new ArrayList<>();
        try {
            Connection connection = connectionManager.getConnection();
            preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_TOPICS_QUERY);
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Topic topic = new Topic();
                topic.setId(resultSet.getInt(1));
                topic.setName(resultSet.getString(2));
                topic.setWordCount(resultSet.getInt(3));
                resultList.add(topic);
            }
            return resultList;
        } catch (SQLException e) {
            logger.error("Can`t get all topics",e);
            throw new DbException("Some problem with data base",e);
        }finally {
            connectionManager.closeResultSet(resultSet);
            connectionManager.closePrepareStatement(preparedStatement);
        }
    }
}
