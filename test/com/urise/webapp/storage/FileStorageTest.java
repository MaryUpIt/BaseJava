package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStreamStorage;

import java.io.File;

class FileStorageTest extends AbstractStorageTest{

    protected FileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new ObjectStreamStorage()));
    }
}