package com.urise.webapp.storage;

import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlAdapter;
import com.urise.webapp.util.Config;

import java.sql.ResultSet;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlAdapter sqlAdapter;
    private static final String URL = Config.getInstance().getUrl();
    private static final String USER = Config.getInstance().getUser();
    private static final String PASSWORD = Config.getInstance().getPassword();
    private static final Comparator<Resume> NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public SqlStorage() {
        this.sqlAdapter = new SqlAdapter(URL, USER, PASSWORD);
    }

    @Override
    public int size() {
        return sqlAdapter.execute("SELECT count(*) size FROM  resume", statement -> {
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new StorageException("Storage is empty");
            }
            return result.getInt("size");
        });
    }

    @Override
    public void save(Resume resume) {
        sqlAdapter.execute("INSERT INTO  resume (uuid, full_name)VALUES (?,?)", statement -> {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
        });
    }

    @Override
    public void update(Resume resume) {
        sqlAdapter.execute("UPDATE resume  SET full_name= ? WHERE uuid=?", statement -> {
            statement.setString(2, resume.getUuid());
            statement.setString(1, resume.getFullName());
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
        });
    }

    @Override
    public void delete(String uuid) {
        sqlAdapter.execute("DELETE FROM resume r WHERE r.uuid =?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlAdapter.execute("SELECT *FROM  resume r WHERE r.uuid =?", statement -> {
            statement.setString(1, uuid);
            ResultSet result = statement.executeQuery();
            if (!result.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, result.getString("full_name"));
        });
    }

    @Override
    public void clear() {
        sqlAdapter.execute("DELETE FROM resume", statement -> {
            statement.execute();
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new LinkedList<>();
        return sqlAdapter.execute("SELECT *FROM  resume", statement -> {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                resumes.add(new Resume(result.getString("uuid"), result.getString("full_name")));
            }
            resumes.sort(NAME_COMPARATOR);
            return resumes;
        });
    }
}
