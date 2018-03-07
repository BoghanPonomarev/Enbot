package ua.nure.ponomarev.holder;

import java.sql.Connection;

/**
 * @author Bogdan_Ponamarev.
 *
 * Holder that carries out the logic of saving connection
 * to every certain thread for transaction
 */
public class SqlConnectionHolder {
    private static ThreadLocal<Connection> threadLocal = new ThreadLocal<>();

    public static Connection getConnection() {
        return threadLocal.get();
    }

    /**
     * Establish new connection in holder
     * @param connection connection that will be established
     */
    public static void setConnection(Connection connection) {
        if (connection != null) {
            threadLocal.set(connection);
        }
    }
}
