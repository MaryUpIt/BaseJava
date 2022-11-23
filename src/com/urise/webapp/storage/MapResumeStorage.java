package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends AbstractMapStorage<Resume> {

    @Override
    protected void doSave(Resume searchKey, Resume resume) {
        map.put(searchKey.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume searchKey, Resume resume) {
        map.put(searchKey.getUuid(), resume);
    }

    @Override
    protected void doDelete(Resume searchKey) {
        map.remove(searchKey.getUuid());
    }

    @Override
    protected Resume doGet(Resume searchKey) {
        return map.get(searchKey.getUuid());
    }

    @Override
    protected Resume findSearchKey(String uuid) {
        return new Resume(uuid, null);
    }

    @Override
    protected boolean isExist(Resume searchKey) {
        return map.containsKey(searchKey.getUuid());
    }
}
