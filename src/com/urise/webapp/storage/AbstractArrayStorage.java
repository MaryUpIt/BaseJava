package com.urise.webapp.storage;

import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    protected final static int STORAGE_LIMIT = 100;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public void doSave(Integer searchKey, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveResume(resume, (int) searchKey);
        size++;
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        storage[(int) searchKey] = resume;
    }

    @Override
    public void doDelete(Integer searchKey) {
        deleteResume((int) searchKey);
        size--;
    }

    @Override
    public Resume doGet(Integer searchKey) {
        return storage[(int) searchKey];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Storage clear");
    }

    @Override
    public List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (int) searchKey >= 0;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

}


