package com.urise.webapp.storage;

import com.urise.webapp.storage.serializers.ObjectStreamSerializer;

class FileStorageTest extends AbstractStorageTest{

    protected FileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ObjectStreamSerializer()));
    }
}