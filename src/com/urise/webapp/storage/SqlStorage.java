package com.urise.webapp.storage;

import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlExecutor;
import com.urise.webapp.util.Config;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlExecutor sqlExecutor;
    private static final String URL = Config.getInstance().getUrl();
    private static final String USER = Config.getInstance().getUser();
    private static final String PASSWORD = Config.getInstance().getPassword();
    private static final Comparator<Resume> NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public SqlExecutor getSqlExecutor() {
        return sqlExecutor;
    }

    public SqlStorage() {
        this.sqlExecutor = new SqlExecutor(URL, USER, PASSWORD);
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
            String uuid = resume.getUuid();
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO  resumes (uuid, full_name)VALUES (?,?)")) {
                statement.setString(1, uuid);
                statement.setString(2, resume.getFullName());
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO  contacts (type, value, resume_uuid) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    statement.setString(1, entry.getKey().name());
                    statement.setString(2, entry.getValue());
                    statement.setString(3, uuid);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlExecutor.transactionalExecute(connection -> {
            String uuid = resume.getUuid();
            try (PreparedStatement statement = connection.prepareStatement("UPDATE resumes SET full_name= ?  WHERE uuid=?")){
                statement.setString(2,uuid);
                statement.setString(1, resume.getFullName());
                if (statement.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try(PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts c WHERE c.resume_uuid =?")){
                statement.setString(1,uuid);
            }

            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO  contacts (type, value, resume_uuid) VALUES (?,?,?)")) {
                for (Map.Entry<ContactType, String> entry : resume.getContacts().entrySet()) {
                    statement.setString(1, entry.getKey().name());
                    statement.setString(2, entry.getValue());
                    statement.setString(3, uuid);
                    statement.addBatch();
                }
                statement.executeBatch();
            }
            return null;
        });

    }

    @Override
    public void delete(String uuid) {
        sqlExecutor.transactionalExecute(connection -> {
            try(PreparedStatement statement = connection.prepareStatement("DELETE FROM resumes r WHERE r.uuid =?")){
                statement.setString(1, uuid);
                if (statement.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            try(PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts c WHERE c.resume_uuid =?")){
                statement.setString(1, uuid);
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
        sqlExecutor.transactionalExecute(connection -> {
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM contacts")) {
                statement.execute();
            }
            try (PreparedStatement statement = connection.prepareStatement("DELETE FROM resumes")) {
                statement.execute();
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> resumes = new LinkedHashMap<>();
        return sqlExecutor.execute("SELECT * FROM resumes JOIN contacts c on resumes.uuid = c.resume_uuid ORDER BY full_name, uuid ", statement -> {
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
}
