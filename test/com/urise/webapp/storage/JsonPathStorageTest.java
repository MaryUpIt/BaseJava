package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.JsonStreamStorage;

import java.io.File;

class JsonPathStorageTest extends AbstractStorageTest {
    protected JsonPathStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new JsonStreamStorage()));
    }
}