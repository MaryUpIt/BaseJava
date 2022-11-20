package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapUuidStorage extends MapStorage {
    private final Map<String, Resume> map = new TreeMap<>();

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




}
