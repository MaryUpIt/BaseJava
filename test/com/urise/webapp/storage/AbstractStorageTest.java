package com.urise.webapp.storage;

import com.urise.webapp.exceptions.ExistStorageException;
import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractStorageTest {

    protected static final String STORAGE_DIR = "C:\\Users\\Mary\\Desktop\\basejava\\resumes";
    final Storage storage;
    private static final String UUID_1 = "uuid1";
    private static final Resume RESUME_1 = new Resume(UUID_1, "Anna");

    private static final String UUID_2 = "uuid2";
    private static final Resume RESUME_2 = new Resume(UUID_2, "Mariya");
    private static final String UUID_3 = "uuid3";
    private static final Resume RESUME_3 = new Resume(UUID_3,"Dmitriy");
    private static final String UUID_4 = "uuid4";
    private static final Resume RESUME_4 = new Resume(UUID_4, "Fedor");




    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }


    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(RESUME_3);
        });

    }

    @Test
    public void update() {
        Resume resume = new Resume(UUID_1, "Ignat");
        storage.update(resume);
  //      assertSame(resume, storage.get(UUID_1));
//        assertNotSame(RESUME_1, storage.get(UUID_1));
//        assertFalse(storage.get(UUID_1) == RESUME_1);
       assertTrue(storage.get(UUID_1).equals(resume));
    }

    @Test
    public void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(RESUME_4);
        });
    }

    @Test
    public void delete() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_2);
            assertSize(2);
            assertGet(RESUME_2);
        });
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(UUID_4);
        });
    }

    @Test
    public void get() {
        Resume resume = storage.get(UUID_1);
        assertEquals(RESUME_1, resume);
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(StorageException.class, () -> {
            storage.get(UUID_4);
        });
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }


    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(RESUME_1, list.get(0));
        assertEquals(RESUME_2, list.get(2));
        assertEquals(RESUME_3, list.get(1));
    }

    private void assertSize(int n) {
        assertEquals(n, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }


}