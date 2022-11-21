package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

abstract public class AbstractMapStorage extends AbstractStorage {
    protected Map<Object, Resume> map = new HashMap<>();


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> getList() {
        return new ArrayList<>(map.values());
    }

}
