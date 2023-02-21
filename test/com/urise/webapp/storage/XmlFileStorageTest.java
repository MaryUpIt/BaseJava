package com.urise.webapp.storage;

import com.urise.webapp.storage.serializers.XmlStreamSerializer;

public class XmlFileStorageTest extends AbstractStorageTest {

    protected XmlFileStorageTest() {
        super(new FileStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }

}