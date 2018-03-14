package ua.nure.ponomarev.service.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.TopicDao;
import ua.nure.ponomarev.entity.Topic;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.service.TopicService;
import ua.nure.ponomarev.transaction.TransactionManager;

import java.util.List;

/**
 * @author Bogdan_Ponamarev.
 */
@NoArgsConstructor
public class TopicServiceImpl implements TopicService {
    private static final Logger logger = LogManager.getLogger(TopicServiceImpl.class);

    private TransactionManager transactionManager;

    private TopicDao topicDao;

    public TopicServiceImpl(TransactionManager transactionManager, TopicDao topicDao) {
        this.topicDao = topicDao;
        this.transactionManager = transactionManager;
    }

    /**
     * Get all topics from data holder
     * @return All topics or empty list
     * @throws DbException if there is problem with data holder
     */
    @Override
    public List<Topic> getAllTopics() throws DbException, ValidationException {
        return transactionManager.doWithTransaction(() -> topicDao.getAllTopics());
    }
}
