package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

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

    @Override
    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Такого резюме нет в хранилище, воспользуйтесь методом save");
        } else {
            storage[index] = resume;
            System.out.println("Обновление резюме: " + resume);
        }

    }

    @Override
    public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            System.arraycopy(storage, index + 1, storage, index, --size - index);
            storage[size] = null;
            System.out.printf("резюме с идентификатором %s удалено из хранилища.\n", uuid);
        } else {
            System.out.printf("резюме с идентификатором %s отсутствует в хранилище.\n", uuid);
        }

    }



    @Override
    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Хранилище очищено");
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
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
