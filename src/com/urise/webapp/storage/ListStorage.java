package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public int getSize() {
        return storage.size();
    }

    @Override
    public void store(Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void updateResume(int index, Resume resume) {
        storage.set(index, resume);
    }

    @Override
    protected void removeResume(int index) {
        storage.remove(index);
    }

    @Override
    public Resume getResume(int index) {
        return storage.get(index);
    }

    @Override
    protected int findIndex(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    public void clear() {
        storage.clear();
        System.out.println("Storage clear");
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(getList().toArray(new Resume[storage.size()]));
    }

    @Override
    public List<Resume> getList() {
        return storage;
    }
}
