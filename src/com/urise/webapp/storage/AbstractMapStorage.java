package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

abstract public class AbstractMapStorage<SearchKey> extends AbstractStorage<SearchKey> {
    protected Map<String, Resume> map = new HashMap<>();


    @Override
    final public int size() {
        return map.size();
    }

    @Override
    final public void clear() {
        map.clear();
    }

    @Override
    final public List<Resume> doGetAll() {
        return new ArrayList<>(map.values());
    }

}
