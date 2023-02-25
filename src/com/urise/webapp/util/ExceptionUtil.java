package com.urise.webapp.util;

import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    public static boolean checkExistence(SQLException e) {
        if (e instanceof PSQLException) {
            return "23505".equals(e.getSQLState());
        }
        throw new RuntimeException("Unsupported Database");
    }
}
