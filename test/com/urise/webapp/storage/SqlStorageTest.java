package com.urise.webapp.storage;

import com.urise.webapp.util.Config;

class SqlStorageTest extends AbstractStorageTest{
    public SqlStorageTest() {
        super(Config.getInstance().getStorage());
    }
}