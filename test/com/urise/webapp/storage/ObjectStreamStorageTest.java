package com.urise.webapp.storage;

import java.io.File;

class ObjectStreamStorageTest extends AbstractStorageTest {

    protected ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(new File(STORAGE_DIR)));
    }
}