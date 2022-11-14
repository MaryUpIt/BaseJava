package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapStorage extends AbstractStorage {
    Map<String, Resume> map = new LinkedHashMap<>();

    @Override
    public int size() {
        return map.size();
    }


    @Override
    protected void doSave(Object searchKey, Resume resume) {
        map.put((String) searchKey, resume);

    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        map.put((String) searchKey, resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(searchKey);
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(searchKey);
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public boolean isExist(Object searchKey) {
        return map.containsKey(searchKey);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public Resume[] getAll() {
        return map.values().toArray(new Resume[map.size()]);
    }

    @Override
    public List<Resume> getList() {
        return new ArrayList<>(map.values());
    }
}
