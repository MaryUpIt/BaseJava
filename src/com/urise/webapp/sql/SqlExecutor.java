package com.urise.webapp.sql;

import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.util.ExceptionUtil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlExecutor {
    public final ConnectionFactory connectionFactory;

    public SqlExecutor(String url, String user, String password) {
        this.connectionFactory = () -> DriverManager.getConnection(url, user, password);
    }

    public <T> T execute(String sqlQuery, SQLStatementFunction<T> function) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlQuery)) {

            return function.executeSQL(statement);
        } catch (SQLException e) {

            throw ExceptionUtil.convertExceptions(e);
        }
    }
    public <T> T transactionalExecute(SqlTransaction<T> transaction) {
        try (Connection connection = connectionFactory.getConnection()) {
            try {
                connection.setAutoCommit(false);
                T resource = transaction.execute(connection);
                connection.commit();
                return resource;
            } catch (SQLException e) {
                connection.rollback();
                throw ExceptionUtil.convertExceptions(e);
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface SQLStatementFunction<T> {
        T executeSQL(PreparedStatement statement) throws SQLException;
    }

    public interface SqlTransaction<T> {
        T execute(Connection connection) throws SQLException;
    }
}
