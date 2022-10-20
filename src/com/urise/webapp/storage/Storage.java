package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public interface Storage {
    int size();
    void save(Resume resume);
    void update(Resume resume);
    void delete(String uuid);
    Resume get(String uuid);
    void clear();

}
