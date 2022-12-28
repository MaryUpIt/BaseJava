package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.DataStreamStorage;

import java.io.File;

public class DataPathStorageTest extends AbstractStorageTest {
    protected DataPathStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new DataStreamStorage()));
    }
}