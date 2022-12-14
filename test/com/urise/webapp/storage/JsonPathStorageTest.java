package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.JsonStreamStorage;

class JsonPathStorageTest extends AbstractStorageTest {

    protected JsonPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new JsonStreamStorage()));
    }
}