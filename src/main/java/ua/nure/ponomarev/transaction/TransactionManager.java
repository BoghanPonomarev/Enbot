package ua.nure.ponomarev.transaction;

import lombok.NoArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.exception.ValidationException;
import ua.nure.ponomarev.holder.SqlConnectionHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Bogdan_Ponamarev.
 * Class that allows carry out transactions
 */

@NoArgsConstructor
public class TransactionManager {

    private DataSource dataSource;
    private final Logger logger = LogManager.getLogger(TransactionManager.class);

    /**
     * Constructor that take data source
     * @param dataSource variable allows take and close connections of data base
     */
    public TransactionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Generic method that take transaction operation as argument and carries
     * the logic of the transaction
     * @param transactionOperation mthod that will be executed with transaction
     * @param <T> Type that will be returned
     * @return result of transaction operation
     * @throws DbException if there are som problem with data base
     */
    public <T> T doWithTransaction(TransactionOperation<T> transactionOperation) throws DbException, ValidationException {
        T result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            SqlConnectionHolder.setConnection(connection);
            if (connection == null) {
                throw new DbException("It can`t get connection",new SQLException());
            }
            connection.setAutoCommit(false);
            result = transactionOperation.execute();
            connection.commit();
        } catch (SQLException e) {
            logger.error(e);
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    logger.error(e1);
                }
            }
        } finally {
            close(connection);
        }
        return result;
    }

    /**
     * A method similar to {@code doWithTransaction} but without transaction logic.
     * Used for transaction operation that need`t transaction but that need creating
     * and closing of connection
     */
    public <T> T doWithoutTransaction(TransactionOperation<T> transactionOperation) throws DbException,ValidationException {
        T result = null;
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
            SqlConnectionHolder.setConnection(connection);
            if (connection == null) {
                throw new DbException("It can`t get connection",new SQLException());
            }
            result = transactionOperation.execute();
        } catch (SQLException e) {
            logger.error(e);
        } finally {
            close(connection);
        }
        return result;
    }

    private void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            logger.error("Connection was not closed closed", e);
        }
    }
}
