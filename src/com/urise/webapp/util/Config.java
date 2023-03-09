package com.urise.webapp.util;

import com.urise.webapp.storage.SqlStorage;
import com.urise.webapp.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final File PROPERTIES = new File(getHomeDir(),"config\\resumes.properties");

    private static final Config INSTANCE = new Config();

    private final File STORAGE_DIR;
    private final Storage STORAGE;
    public static Config getInstance() {
        return INSTANCE;
    }


    private Config() {
        try (InputStream is = new FileInputStream(PROPERTIES)) {
            Properties properties = new Properties();
            properties.load(is);
            STORAGE_DIR = new File(properties.getProperty("storage.dir"));
            STORAGE = new SqlStorage(properties.getProperty("db.url"),
                    properties.getProperty("db.user"), properties.getProperty("db.password"));

        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES.getAbsolutePath());
        }
    }

    private static File getHomeDir() {
        String properties = System.getProperty("homeDir");
        File homeDir = new File(properties == null ? "." : properties);
        if (!homeDir.isDirectory()) {
            throw new IllegalStateException(homeDir + " is not directory");
        }
        return homeDir;
    }

    public File getStorageDir() {
        return STORAGE_DIR;
    }
    public Storage getStorage(){return STORAGE;}


}
