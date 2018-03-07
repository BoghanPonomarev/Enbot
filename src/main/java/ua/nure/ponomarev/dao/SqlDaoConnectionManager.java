package ua.nure.ponomarev.dao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.nure.ponomarev.exception.DbException;
import ua.nure.ponomarev.holder.SqlConnectionHolder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.util.Objects.nonNull;

/**
 * @author Bogdan_Ponamarev.
 */
public class SqlDaoConnectionManager {
    private static Logger logger = LogManager.getLogger(SqlDaoConnectionManager.class);
    private DataSource dataSource;

    public SqlDaoConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() throws SQLException {

        Connection connection = SqlConnectionHolder.getConnection();
        if (connection == null) {
            connection = dataSource.getConnection();
        }
        return connection;
    }

    public void closeResultSet(ResultSet resultSet) throws DbException {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (SQLException e) {
            throw new DbException("Result set has`nt been closed",e);
        }
    }

    public void closePrepareStatement(PreparedStatement preparedStatement) throws DbException {
        if (preparedStatement != null) {
            try {
                preparedStatement.close();
            } catch (SQLException e) {
                throw new DbException("Prepared statement has`nt been closed",e);
            }
        }
    }

    public void closeConnection(Connection connection) throws DbException {
        if(nonNull(SqlConnectionHolder.getConnection())){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DbException("Connection has`nt been closed",e);
            }
        };
    }
}
