package com.urise.webapp.storage;

import static org.junit.jupiter.api.Assertions.*;

class SqlStorageTest extends AbstractStorageTest{

    public SqlStorageTest() {
        super(new SqlStorage());
    }
}