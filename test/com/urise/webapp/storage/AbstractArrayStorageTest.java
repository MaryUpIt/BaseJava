package com.urise.webapp.storage;

import com.urise.webapp.exceptions.ExistStorageException;
import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class AbstractArrayStorageTest {
    private Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1);
    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2);
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3);
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4);

    protected AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }


    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void saveNotExist() throws Exception {
        storage.save(new Resume(UUID_4));
        Resume[] checkStorage = {RESUME_1, RESUME_2, RESUME_3, RESUME_4};
        Assert.assertEquals(4, storage.size());
        Assert.assertArrayEquals(checkStorage, storage.getAll());
    }

    @Test(expected = ExistStorageException.class)
    public void saveExist() throws Exception {
        storage.save(new Resume(UUID_3));
    }

    @Test(expected = StorageException.class)
    public void saveOverFlow() throws Exception {
        for (int i = storage.size(); i <= AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume());
            } catch (ExistStorageException e) {
                Assert.fail(e.getMessage());
            }
        }
    }

    @Test
    public void update() throws Exception {
        storage.update(RESUME_1);
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
    }


    @Test(expected = NotExistStorageException.class)
    public void updateNotExist() throws Exception {
        storage.update(RESUME_4);
    }

    @Test
    public void delete() throws Exception {
        storage.delete(UUID_2);
        Resume[] checkStorage = {RESUME_1, RESUME_3};
        Assert.assertEquals(2, storage.size());
        Assert.assertArrayEquals(checkStorage, storage.getAll());
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteNotExist() throws Exception {
        storage.delete(UUID_4);
    }

    @Test
    public void get() throws Exception {
        storage.get(UUID_1);
        Assert.assertEquals(RESUME_1, storage.get(UUID_1));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get(UUID_4);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void getAll() {
        Resume[] checkStorage = {RESUME_1, RESUME_2, RESUME_3};
        Assert.assertArrayEquals(checkStorage, storage.getAll());
    }
}