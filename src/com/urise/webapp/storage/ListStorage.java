package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }


    @Override
    public void doSave(Object searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        storage.remove((int) searchKey);

    }

    @Override
    protected Resume doGet(Object searchKey) {
        return storage.get((int) searchKey);
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

    @Override
    protected Object findSearchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    public boolean isExist(Object searchKey) {
        if ((int) searchKey >= 0) {
            return true;
        }
        return false;
    }
}
