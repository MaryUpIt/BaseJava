package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.JsonStreamStorage;

import java.io.File;

public class JsonFileStorageTest extends AbstractStorageTest {
    protected JsonFileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new JsonStreamStorage()));
    }
}
