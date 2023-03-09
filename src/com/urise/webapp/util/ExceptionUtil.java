package com.urise.webapp.util;

import com.urise.webapp.exceptions.ExistStorageException;
import com.urise.webapp.exceptions.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.SQLException;

public class ExceptionUtil {
    private static boolean checkExistence(SQLException e) {
        if (e instanceof PSQLException) {
            return "23505".equals(e.getSQLState());
        }
        throw new RuntimeException("Unsupported Database");
    }

    public static StorageException convertExceptions(SQLException e) {
        if (checkExistence(e)) {
            return new ExistStorageException(e.getMessage());
        }
        return new StorageException(e.getMessage());
    }

}
