package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage {

    @Override
    protected void doSave(Object searchKey, Resume resume) {
        map.put(((Resume)searchKey).getUuid(), resume);

    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {
        map.put(((Resume)searchKey).getUuid(), resume);
    }

    @Override
    protected void doDelete(Object searchKey) {
        map.remove(((Resume)searchKey).getUuid());
    }

    @Override
    protected Resume doGet(Object searchKey) {
        return map.get(((Resume)searchKey).getUuid());
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return new Resume(uuid, null);
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return map.containsKey(((Resume)searchKey).getUuid());
    }


}
