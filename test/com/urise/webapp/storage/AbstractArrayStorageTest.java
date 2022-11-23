package com.urise.webapp.storage;

import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public abstract class  AbstractArrayStorageTest extends AbstractStorageTest {

    protected AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }
    @Test
    public void saveOverFlow() {
        Assertions.assertThrows(StorageException.class, () -> {
            storage.clear();
            for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
                try {
                    storage.save(new Resume(null));
                } catch (StorageException e) {
                    fail(e.getMessage());
                }
            }
            storage.save(new Resume(null));
        });
    }
}