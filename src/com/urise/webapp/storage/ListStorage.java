package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    public int size() {
        return storage.size();
    }


    @Override
    public void doSave(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Integer searchKey, Resume resume) {
        storage.set((int) searchKey, resume);
    }

    @Override
    protected void doDelete(Integer searchKey) {
        storage.remove((int) searchKey);

    }

    @Override
    protected Resume doGet(Integer searchKey) {
        return storage.get((int) searchKey);
    }

    @Override
    public void clear() {
        storage.clear();
        System.out.println("Storage clear");
    }

    @Override
    public List<Resume> doGetAll() {
        return storage;
    }

    @Override
    protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer searchKey) {
        return (int) searchKey >= 0;
    }
}
