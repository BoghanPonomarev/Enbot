package ua.nure.ponomarev.service;

import ua.nure.ponomarev.entity.Topic;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;

import java.util.List;
/**
 * @author Bogdan_Ponamarev.
 */
public interface TopicService {

    List<Topic> getAllTopics() throws DbException, ValidationException;
}
