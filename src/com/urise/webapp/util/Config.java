package com.urise.webapp.util;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPERTIES = new File("config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private Properties properties = new Properties();
    private File storageDir;
    private String url;
    private String user;
    private String password;
    private Storage storage;

    public static Config getInstance() {
        return INSTANCE;
    }

    private Config() {
        try (InputStream is = new FileInputStream(PROPERTIES)) {
            properties.load(is);
            storageDir = new File(properties.getProperty("storage.dir"));
            url = properties.getProperty("db.url");
            user = properties.getProperty("db.user");
            password = properties.getProperty("db.password");
            storage = new SqlStorage(url,user,password);

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES.getAbsolutePath());
        }
    }

    public File getStorageDir() {
        return storageDir;
    }

    public String getUrl() {
        return url;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public Storage getStorage() {
        return storage;
    }
}
