package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {
    @Override
    public void save(Resume resume) {
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено");
        } else if (findIndex(resume.getUuid()) != -1) {
            System.out.println("Такое резюме уже есть в хранилище,  воспользуйтесь методом update");
        } else {
            storage[size++] = resume;
            System.out.println("Добавлено резюме: " + resume);
        }
    }
    protected int findIndex(String uuid) {
        Resume resume = new Resume(uuid);
        for (int i = 0; i < size; i++) {
            if (storage[i].equals(resume)) {
                return i;
            }
        }
        return -1;
    }
}
