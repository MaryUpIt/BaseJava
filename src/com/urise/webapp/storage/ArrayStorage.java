package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    final protected Integer findSearchKey(String uuid) {
        for (int i = 0; i < size; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    final protected void saveResume(Resume resume, int index) {
        storage[size] = resume;
    }

    @Override
    final protected void deleteResume(int index) {
        storage[index] = storage[size - 1];
    }
}

