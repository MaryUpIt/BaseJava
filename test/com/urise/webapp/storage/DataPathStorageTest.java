package com.urise.webapp.storage;

import com.urise.webapp.storage.serializers.DataStreamSerializer;

public class DataPathStorageTest extends AbstractStorageTest {
    protected DataPathStorageTest() {
        super(new FileStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}