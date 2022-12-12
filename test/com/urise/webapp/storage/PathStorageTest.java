package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.ObjectStreamStorage;

class PathStorageTest extends AbstractStorageTest{

    protected PathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new ObjectStreamStorage()));
    }
}