package com.urise.webapp.storage;

import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {
    protected final static int STORAGE_LIMIT = 100;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    int size;

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public void store(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            throw new StorageException("Storage overflow", resume.getUuid());
        }
        saveResume(resume, index);
        size++;
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage[index] = resume;

    }

    @Override
    public void removeResume(int index) {
        deleteResume(index);
        size--;
        storage[size] = null;
    }

    @Override
    public Resume getResume(int index) {
        return storage[index];
    }

    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Storage clear");
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    @Override
    public List<Resume> getList() {
        List<Resume> storage = new ArrayList<>();
        Collections.addAll(storage, getAll());
        return storage;
    }

    protected abstract void saveResume(Resume resume, int index);

    protected abstract void deleteResume(int index);
}


