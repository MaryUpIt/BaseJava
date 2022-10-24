package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено");
        } else if (findIndex(resume.getUuid()) >= 0) {
            System.out.println("Такое резюме уже есть в хранилище,  воспользуйтесь методом update");
        } else {
            int index = Math.abs(findIndex(resume.getUuid())) - 1;
            if (index >= size) {
                storage[index] = resume;
            } else {
                System.arraycopy(storage, index, storage, index + 1, size - index);
                storage[index]= resume;
            }
            size++;
            System.out.println("Добавлено резюме: " + resume);
        }
    }


    @Override
    protected int findIndex(String uuid) {
        return Arrays.binarySearch(storage, 0, size, new Resume(uuid));
    }
}
