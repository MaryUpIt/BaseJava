package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

abstract public class AbstractMapStorage<SearchKey> extends AbstractStorage<SearchKey> {
    protected Map<String, Resume> map = new HashMap<>();


    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<>(map.values());
    }

}
