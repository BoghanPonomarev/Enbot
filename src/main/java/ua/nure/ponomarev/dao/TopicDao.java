package ua.nure.ponomarev.dao;
import ua.nure.ponomarev.entity.Topic;
import ua.nure.ponomarev.exception.DbException;

import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public interface TopicDao {
    List<Topic> getAllTopics() throws DbException;
}
