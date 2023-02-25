package com.urise.webapp.storage;

import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlExecutor;
import com.urise.webapp.util.Config;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SqlStorage implements Storage {
    public final SqlExecutor sqlExecutor;
    private static final String URL = Config.getInstance().getUrl();
    private static final String USER = Config.getInstance().getUser();
    private static final String PASSWORD = Config.getInstance().getPassword();
    private static final Comparator<Resume> NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public SqlStorage() {
        this.sqlExecutor = new SqlExecutor(URL, USER, PASSWORD);
    }

    @Override
    public int size() {
        return sqlExecutor.execute("SELECT count(*) size FROM  resume", statement -> {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                return result.getInt("size");
            }
            return 0;
        });
    }

    @Override
    public void save(Resume resume) {
        sqlExecutor.execute("INSERT INTO  resume (uuid, full_name)VALUES (?,?)", statement -> {
            statement.setString(1, resume.getUuid());
            statement.setString(2, resume.getFullName());
            statement.execute();
            return null;
        });
    }

    @Override
    public void update(Resume resume) {
        sqlExecutor.execute("UPDATE resume  SET full_name= ? WHERE uuid=?", statement -> {
            statement.setString(2, resume.getUuid());
            statement.setString(1, resume.getFullName());
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(resume.getUuid());
            }
            return null;
        });
    }

    @Override
    public void delete(String uuid) {
        sqlExecutor.execute("DELETE FROM resume r WHERE r.uuid =?", statement -> {
            statement.setString(1, uuid);
            if (statement.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlExecutor.execute("SELECT *FROM  resume r WHERE r.uuid =?", statement -> {
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
        sqlExecutor.execute("DELETE FROM resume", statement -> {
            statement.execute();
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        return sqlExecutor.execute("SELECT * FROM resume ORDER BY full_name, uuid ", statement -> {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                resumes.add(new Resume(result.getString("uuid"), result.getString("full_name")));
            }
            return resumes;
        });
    }
}
