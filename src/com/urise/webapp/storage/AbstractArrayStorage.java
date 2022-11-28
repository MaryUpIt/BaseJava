package com.urise.webapp.storage;

import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    public final static int STORAGE_LIMIT = 10;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    int size;

    @Override
    final public int size() {
        return size;
    }

    @Override
    final public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Storage clear");
    }

    @Override
    final protected void doSave(Integer searchKey, Resume resume) {
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveResume(resume, searchKey);
        size++;
    }

    @Override
    final protected void doUpdate(Integer searchKey, Resume resume) {
        storage[searchKey] = resume;
    }

    @Override
    final protected void doDelete(Integer searchKey) {
        deleteResume(searchKey);
        size--;
    }

    @Override
    final protected Resume doGet(Integer searchKey) {
        return storage[searchKey];
    }

    @Override
    final protected List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOf(storage, size));
    }

    @Override
    final protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);

}


