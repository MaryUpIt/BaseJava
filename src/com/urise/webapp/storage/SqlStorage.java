package com.urise.webapp.storage;

import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlExecutor;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {
    public final SqlExecutor sqlExecutor;

    public SqlStorage(String url, String user, String password) {
        this.sqlExecutor = new SqlExecutor(() -> DriverManager.getConnection(url, user, password));
    }

    @Override
    public int size() {
        return sqlExecutor.execute("SELECT count(*) size FROM  resumes", statement -> {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("size");
            }
            return 0;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlExecutor.transactionalExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO  resumes (uuid, full_name)VALUES (?,?)")) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }
            insertContacs(connection, resume);

            return null;
        });
    }

    @Override
    public void update(Resume resume) {

        sqlExecutor.transactionalExecute(connection -> {
            String uuid = resume.getUuid();
            try (PreparedStatement statement = connection.prepareStatement(
                    "UPDATE resumes  SET full_name= ? WHERE uuid=?")) {
                statement.setString(2, uuid);
                statement.setString(1, resume.getFullName());
                if (statement.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteContacts(uuid);
            insertContacs(connection, resume);
            return null;
        });

    }

    @Override
    public void delete(String uuid) {
        sqlExecutor.execute("DELETE FROM resumes r  WHERE r.uuid =?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlExecutor.execute("SELECT *FROM  resumes r  " +
                "JOIN contacts c " +
                "ON r.uuid = c.resume_uuid " +
                "WHERE r.uuid =?", statement -> {
            statement.setString(1, uuid);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new NotExistStorageException(uuid);
            }
            Resume resume = new Resume(uuid, result.getString("full_name"));
            do {
                String value = result.getString("value");
                ContactType type = ContactType.valueOf(result.getString("type"));
                resume.addContact(type, value);
            } while (result.next());
            return resume;
        });
    }

    @Override
    public void clear() {
        sqlExecutor.execute("DELETE FROM resumes", statement -> {
            statement.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        return sqlExecutor.execute("SELECT * FROM resumes " +
                "LEFT JOIN contacts c " +
                "ON resumes.uuid = c.resume_uuid  " +
                "ORDER BY full_name, uuid ", statement -> {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String uuid = result.getString("uuid");
                String value = result.getString("value");
                ContactType type = ContactType.valueOf(result.getString("type"));
                if (!resumes.containsKey(uuid)) {
                    Resume resume = new Resume(uuid, result.getString("full_name"));
                    resume.addContact(type, value);
                    resumes.put(uuid, resume);

                } else {
                    Resume resume = resumes.get(uuid);
                    resume.addContact(type, value);
                }
            }
            return new ArrayList<>(resumes.values());
        });
    }

    private void insertContacs(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO  contacts (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                statement.setString(1, entry.getKey().name());
                statement.setString(2, entry.getValue());
                statement.setString(3, resume.getUuid());
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }

    private void deleteContacts(String uuid) {
        sqlExecutor.execute("DELETE FROM contacts c WHERE c.resume_uuid =?", statement -> {
            statement.setString(1, uuid);
            return null;
        });
    }
}
