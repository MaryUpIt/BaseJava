package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

   // private static final Comparator<Resume> UUID_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    final protected Integer findSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid, "NOT_NAME"));
    }


    @Override
    final protected void saveResume(Resume resume, int index) {
        index = Math.abs(index) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;
    }

    @Override
    final protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);
    }
}



