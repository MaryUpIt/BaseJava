package com.urise.webapp.storage;

import com.urise.webapp.storage.strategy.XmlStreamStorage;

import java.io.File;

public class XmlFileStorageTest extends AbstractStorageTest {

    protected XmlFileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new XmlStreamStorage()));
    }

}