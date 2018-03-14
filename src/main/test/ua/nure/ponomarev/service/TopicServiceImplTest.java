package ua.nure.ponomarev.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import ua.nure.ponomarev.TopicDao;
import ua.nure.ponomarev.entity.Topic;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.service.impl.TopicServiceImpl;
import ua.nure.ponomarev.transaction.TransactionManager;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Bogdan_Ponamarev.
 */
@RunWith(MockitoJUnitRunner.class)
public class TopicServiceImplTest {

    @Mock
    private DataSource dataSource;

    @Mock
    private TopicDao topicDao;
    @InjectMocks
    private TransactionManager transactionManager = new TransactionManager(dataSource);
    @InjectMocks
    private TopicServiceImpl topicService = new TopicServiceImpl(transactionManager, topicDao);

    @Before
    public void before() throws SQLException {
        when(dataSource.getConnection()).thenReturn(mock(Connection.class));
    }

    @Test
    public void GET_ALL_TOPICS_EMTY_LIST_TEST() throws DbException, ValidationException {
        List<Topic> emptyResult = new ArrayList<>();
        when(topicDao.getAllTopics()).thenReturn(emptyResult);
        Assert.assertEquals(emptyResult,topicService.getAllTopics());
    }

    @Test
    public void GET_ALL_TOPICS_TEST() throws SQLException, DbException, ValidationException {
        List<Topic> expectedTopics = getExpectedTopics();
        when(topicDao.getAllTopics()).thenReturn(expectedTopics);

        Assert.assertEquals(expectedTopics, topicService.getAllTopics());
    }

    private List<Topic> getExpectedTopics() {
        List<Topic> topicsExpected = new ArrayList<>();
        topicsExpected.add(new Topic(1, "Stages in life", 10));
        topicsExpected.add(new Topic(2, "How to life", 100));
        topicsExpected.add(new Topic(3, "Hello world", 23));
        return topicsExpected;
    }

}
