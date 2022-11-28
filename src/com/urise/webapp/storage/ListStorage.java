package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;


public class ListStorage extends AbstractStorage<Integer> {
    protected List<Resume> storage = new ArrayList<>();

    @Override
    final public int size() {
        return storage.size();
    }

    @Override
    final public void clear() {
        storage.clear();
        System.out.println("Storage clear");
    }

    @Override
    final public List<Resume> doGetAll() {
        return storage;
    }


    @Override
    final public void doSave(Integer searchKey, Resume resume) {
        storage.add(resume);
    }

    @Override
    final protected void doUpdate(Integer searchKey, Resume resume) {
        storage.set(searchKey, resume);
    }

    @Override
    final protected void doDelete(Integer searchKey) {
        storage.remove((int)searchKey);
        //System.out.println(storage.remove());

    }

    @Override
    final protected Resume doGet(Integer searchKey) {
        return storage.get(searchKey);
    }

    @Override
    final protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (uuid.equals(storage.get(i).getUuid())) {
                return i;
            }
        }
        return -1;
    }

    @Override
    final protected boolean isExist(Integer searchKey) {
        return searchKey >= 0;
    }
}
