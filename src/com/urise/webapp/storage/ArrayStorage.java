package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int size;

    public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Хранилище очищено");
    }

    public void save(Resume resume) {
        if (findIndex(resume.getUuid()) != -1) {
            System.out.println("Такое резюме уже есть в хранилище,  воспользуйтесь методом update");
            return;
        }
        if (resume == null) {
            System.out.println("Введите uuid резюме");
            return;
        }
        if (size < storage.length) {
            storage[size++] = resume;
            System.out.println("Добавлено резюме: " + resume);

        } else {
            System.out.println("Хранилище переполнено");
        }
    }

    public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index == -1) {
            System.out.println("Такого резюме нет в хранилище, воспользуйтесь методом save");
        } else {
            storage[index] = resume;
            System.out.println("Обновление резюме: " + resume);
        }

    }

    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("резюме с идентификатором %s отсутствует в хранилище.\n", uuid);
        return null;
    }

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

    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    public int size() {
        return size;
    }

    private int findIndex(String uuid) {
        int index = 0;
        for (Resume resume : getAll()) {
            if (resume.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
