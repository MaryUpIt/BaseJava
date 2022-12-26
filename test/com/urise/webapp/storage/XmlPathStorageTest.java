package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.XmlStreamStorage;

public class XmlPathStorageTest extends AbstractStorageTest {

    protected XmlPathStorageTest() {
        super(new PathStorage(STORAGE_DIR, new XmlStreamStorage()));
    }
}