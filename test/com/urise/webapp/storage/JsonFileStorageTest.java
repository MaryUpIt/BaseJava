package com.urise.webapp.storage;

import com.urise.webapp.storage.serializers.JsonStreamSerializer;

public class JsonFileStorageTest extends AbstractStorageTest {
    protected JsonFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new JsonStreamSerializer()));
    }
}
