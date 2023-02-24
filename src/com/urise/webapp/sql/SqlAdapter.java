package com.urise.webapp.sql;

import com.urise.webapp.exceptions.StorageException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlAdapter {
    public final ConnectionFactory connectionFactory;

    public SqlAdapter(String url, String user, String password) {
        this.connectionFactory = () -> DriverManager.getConnection(url, user, password);
    }

    public <T> T execute(String sqlCommand, StatementFunction<T> function) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            return function.apply(statement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public void execute(String sqlCommand, StatementConsumer consumer) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement statement = connection.prepareStatement(sqlCommand)) {
            consumer.accept(statement);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public interface StatementFunction<T> {
        T apply(PreparedStatement statement) throws SQLException;
    }

    public interface StatementConsumer {
        void accept(PreparedStatement statement) throws SQLException;
    }
}
