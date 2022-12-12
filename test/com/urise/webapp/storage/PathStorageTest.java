package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStreamStorage;

import java.nio.file.Paths;

class PathStorageTest extends AbstractStorageTest{

    protected PathStorageTest() {
        super(new PathStorage(Paths.get(STORAGE_DIR), new ObjectStreamStorage()));
    }
}