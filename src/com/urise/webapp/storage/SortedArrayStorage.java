package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {

    private static final Comparator<Resume> UUID_COMPARATOR = (resume1, resume2) -> resume1.getUuid().compareTo(resume2.getUuid());

    @Override
    protected Object findSearchKey(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid), UUID_COMPARATOR);
    }


    @Override
    protected void saveResume(Resume resume, int index) {
        index = Math.abs(index) - 1;
        System.arraycopy(storage, index, storage, index + 1, size - index);
        storage[index] = resume;

    }

    @Override
    protected void deleteResume(int index) {
        System.arraycopy(storage, index + 1, storage, index, size - index - 1);

    }
}



