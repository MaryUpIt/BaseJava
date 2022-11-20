package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class MapResumeStorage extends MapStorage{

    @Override
    protected void doSave(Object searchKey, Resume resume) {

    }

    @Override
    protected void doUpdate(Object searchKey, Resume resume) {

    }

    @Override
    protected void doDelete(Object searchKey) {

    }

    @Override
    protected Resume doGet(Object searchKey) {
        return null;
    }

    @Override
    protected Object findSearchKey(String uuid) {
        return null;
    }

    @Override
    protected boolean isExist(Object searchKey) {
        return false;
    }
}
