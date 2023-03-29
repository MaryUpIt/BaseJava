package com.urise.webapp.storage;

import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlExecutor;
import com.urise.webapp.util.JsonParser;

import java.sql.*;
import java.util.*;

public class SqlStorage implements Storage {
    public final SqlExecutor sqlExecutor;

    public SqlStorage(String url, String user, String password) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        this.sqlExecutor = new SqlExecutor(() -> DriverManager.getConnection(
                url , user, password));

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
            try (PreparedStatement statement = connection.prepareStatement("INSERT INTO  resumes (uuid, full_name)VALUES (?,?)")) {
                statement.setString(1, resume.getUuid());
                statement.setString(2, resume.getFullName());
                statement.execute();
            }
            insertContacts(connection, resume);
            insertSections(connection, resume);

            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlExecutor.transactionalExecute(connection -> {
            String uuid = resume.getUuid();
            try (PreparedStatement statement = connection.prepareStatement("UPDATE resumes SET full_name= ?  WHERE uuid= ?")) {
                statement.setString(2, uuid);
                statement.setString(1, resume.getFullName());
                if (statement.executeUpdate() == 0) {
                    throw new NotExistStorageException(uuid);
                }
            }
            deleteContent(connection, uuid, "contacts");
            insertContacts(connection, resume);
            deleteContent(connection, uuid, "sections");
            insertSections(connection, resume);
            return null;
        });

    }

    @Override
    public void delete(String uuid) {
        sqlExecutor.execute("DELETE FROM resumes r WHERE r.uuid =?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlExecutor.transactionalExecute(connection -> {
            Resume resume = sqlExecutor.execute("SELECT * FROM  resumes WHERE uuid=?", statement -> {
                statement.setString(1, uuid);
                ResultSet result = statement.executeQuery();
                if (!result.next()) throw new NotExistStorageException(uuid);
                return new Resume(uuid, result.getString("full_name"));
            });

            setContacts(resume);
            setSections(resume);
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
        return sqlExecutor.execute("SELECT * FROM resumes ORDER BY full_name", statement -> {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                String uuid = result.getString("uuid");
                Resume resume = new Resume(uuid, result.getString("full_name"));
                setContacts(resume);
                setSections(resume);
                resumes.put(uuid, resume);
            }
            return new ArrayList(resumes.values());
        });
    }

    private void insertContacts(Connection connection, Resume resume) throws SQLException {
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

    private void insertSections(Connection connection, Resume resume) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO  sections (type, value, resume_uuid) VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> entry : resume.getSections().entrySet()) {
                SectionType type = entry.getKey();
                AbstractSection section = entry.getValue();
                statement.setString(1, type.name());
                statement.setString(2, JsonParser.write(section, AbstractSection.class));
                statement.setString(3, resume.getUuid());
//                switch (type) {
//                    case PERSONAL, OBJECTIVE -> statement.setString(2, ((TextSection) entry.getValue()).getContent());
//
//                    case ACHIEVEMENT, QUALIFICATIONS -> {
//                        ListSection list = (ListSection) entry.getValue();
//                        String content = "";
//                        for (String text : list.getContent()) {
//                            content += text + "\n";
//                        }
//                        statement.setString(2, content);
//                    }
//                    // case EXPERIENCE, EDUCATION -> {}
//                    default -> throw new IllegalStateException();
//                }
                statement.addBatch();
            }
            statement.executeBatch();
        }
    }


    private void setContacts(Resume resume) {
        sqlExecutor.execute("SELECT * FROM contacts WHERE resume_uuid =?", statement -> {
            statement.setString(1, resume.getUuid());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                ContactType type = ContactType.valueOf(result.getString("type"));
                resume.setContact(type, result.getString("value"));
            }
            return null;
        });
    }

    private void setSections(Resume resume) {
        sqlExecutor.execute("SELECT * FROM sections WHERE  resume_uuid =?", statement -> {
            statement.setString(1, resume.getUuid());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                SectionType type = SectionType.valueOf(result.getString("type"));
                String value = result.getString("value");
                resume.setSection(type, JsonParser.read(value, AbstractSection.class));
//                switch (type) {
//                    case PERSONAL, OBJECTIVE -> resume.addSection(type, new TextSection(value));
//
//                    case ACHIEVEMENT, QUALIFICATIONS ->
//                            resume.addSection(type, new ListSection(Arrays.asList(value.split("\\r?\\n"))));
//                    // case EDUCATION, EXPERIENCE -> {}
//                    default -> throw new IllegalStateException();
//                }
            }
            return null;
        });
    }

    private void deleteContent(Connection connection, String uuid, String tableName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM " + tableName + " t WHERE t.resume_uuid =?")) {
            statement.setString(1, uuid);
            statement.execute();
        }
    }


}
