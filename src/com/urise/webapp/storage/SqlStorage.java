package com.urise.webapp.storage;

import com.urise.webapp.exceptions.ExistStorageException;
import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.util.Config;

import java.sql.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SqlStorage implements Storage {
    public final ConnectionFactory connectionFactory;
    private static final String URL = Config.getInstance().getUrl();
    private static final String USER = Config.getInstance().getUser();
    private static final String PASSWORD = Config.getInstance().getPassword();
    public static final Comparator<Resume> NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public SqlStorage() {
        this.connectionFactory = () -> DriverManager.getConnection(URL, USER, PASSWORD);
    }

    @Override
    public int size() {
        //SELECT count(*) FROM jagers;
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement prepared = connection.prepareStatement("SELECT *FROM  resume")) {
            ResultSet result = prepared.executeQuery();
            int count = 0;
            while (result.next()) {
                count++;
            }
            return count;
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }


    @Override
    public void save(Resume resume) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement prepared = connection.prepareStatement("INSERT INTO  resume (uuid, full_name)VALUES (?,?)")) {
            prepared.setString(1, resume.getUuid());
            prepared.setString(2, resume.getFullName());
            prepared.execute();
        } catch (SQLException e) {
            throw new ExistStorageException(e.getMessage());
        }
    }

    @Override
    public void update(Resume resume) {
        // UPDATE jagers SET kaijuKill = kaijuKill + 1 WHERE status = 'active';
        get(resume.getUuid());
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement prepared = connection.prepareStatement("UPDATE resume  SET full_name= ? WHERE uuid=?")) {
            prepared.setString(2, resume.getUuid());
            prepared.setString(1, resume.getFullName());
            prepared.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }

    }

    @Override
    public void delete(String uuid) {
        get(uuid);
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement prepared = connection.prepareStatement("DELETE FROM resume r WHERE r.uuid =?")) {
            prepared.setString(1, uuid);
            prepared.execute();

        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public Resume get(String uuid) {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement prepared = connection.prepareStatement("SELECT *FROM  resume r WHERE r.uuid =?")) {
            prepared.setString(1, uuid);
            ResultSet result = prepared.executeQuery();
            if (!result.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(uuid, result.getString("full_name"));
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void clear() {
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement prepared = connection.prepareStatement("DELETE FROM resume")) {
            prepared.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        try (Connection connection = connectionFactory.getConnection();
             PreparedStatement prepared = connection.prepareStatement("SELECT *FROM  resume")) {
            ResultSet result = prepared.executeQuery();
            while (result.next()) {
                resumes.add(new Resume(result.getString("uuid"), result.getString("full_name")));
            }
            resumes.sort(NAME_COMPARATOR);
            return resumes;
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
