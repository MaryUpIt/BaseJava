package com.urise.webapp.storage;

import com.urise.webapp.storage.serializers.ObjectStreamSerializer;

class PathStorageTest extends AbstractStorageTest{

    protected PathStorageTest() {
        super(new PathStorage(STORAGE_DIR.getAbsolutePath(), new ObjectStreamSerializer()));
    }
}