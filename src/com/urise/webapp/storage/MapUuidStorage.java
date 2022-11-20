package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapUuidStorage extends AbstractMapStorage {

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
    protected String findSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey(searchKey);
    }


}
