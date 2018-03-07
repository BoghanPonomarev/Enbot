package ua.nure.ponomarev.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.dao.TopicDao;
import ua.nure.ponomarev.entity.Topic;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.service.TopicService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */

public class TopicServiceImpl implements TopicService {
    private static final Logger logger = LogManager.getLogger(TopicServiceImpl.class);

    private TransactionManager transactionManager;

    private TopicDao topicDao;

    public TopicServiceImpl(TransactionManager transactionManager, TopicDao topicDao) {
        this.topicDao = topicDao;
        this.transactionManager = transactionManager;
    }

    @Override
    public List<Topic> getAllTopics() throws DbException, ValidationException {
        return transactionManager.doWithTransaction(() -> topicDao.getAllTopics());
    }
}
