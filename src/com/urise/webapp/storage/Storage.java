package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.List;

public interface Storage {
    int size();
    void save(Resume resume);
    void update(Resume resume);
    void delete(String uuid);
    Resume get(String uuid);
    void clear();
  //  Resume[] getAll();
    List<Resume> getAllSorted();

}
