package com.urise.webapp.sql;

import com.urise.webapp.exceptions.ExistStorageException;
import com.urise.webapp.exceptions.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.urise.webapp.util.ExceptionUtil.checkExistence;

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
            if (checkExistence(e)) {
                throw new ExistStorageException(null);
            }
            throw new StorageException(e.getMessage());
        }
    }

    public interface SQLStatementFunction<T> {
        T executeSQL(PreparedStatement statement) throws SQLException;
    }
}
