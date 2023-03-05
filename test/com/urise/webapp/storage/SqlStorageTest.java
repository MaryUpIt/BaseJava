package com.urise.webapp.storage;

import com.urise.webapp.util.Config;

class SqlStorageTest extends AbstractStorageTest{
    private static final String URL = Config.getInstance().getUrl();
    private static final String USER = Config.getInstance().getUser();
    private static final String PASSWORD = Config.getInstance().getPassword();

    public SqlStorageTest() {
        super(new SqlStorage(URL,USER,PASSWORD));
    }
}