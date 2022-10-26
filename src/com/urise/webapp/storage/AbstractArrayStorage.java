package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_LIMIT = 10_000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    @Override
    final public int size() {
        return size;
    }

    @Override
    final public void save(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (size >= STORAGE_LIMIT) {
            System.out.println("Хранилище переполнено");
        } else if ( index >= 0) {
            System.out.println("Такое резюме уже есть в хранилище,  воспользуйтесь методом update");
        } else {
            saveResume(resume);
            System.out.println("Резюме " + resume + " добавлено в хранилище");
        }
    }

    @Override
    final public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            System.out.println("Такого резюме нет в хранилище, воспользуйтесь методом save");
        } else {
            storage[index] = resume;
            System.out.println("Обновление резюме: " + resume);
        }
    }

    @Override
    final public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            deleteResume(index);
            System.out.printf("резюме с идентификатором %s удалено из хранилища.\n", uuid);
        } else {
            System.out.printf("резюме с идентификатором %s отсутствует в хранилище.\n", uuid);
        }
    }

    @Override
    final public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index >= 0) {
            return storage[index];
        }
        System.out.printf("резюме с идентификатором %s отсутствует в хранилище.\n", uuid);
        return null;
    }

    @Override
    final public void clear() {
        Arrays.fill(storage, 0, size, null);
        size = 0;
        System.out.println("Хранилище очищено");

    }

    @Override
    final public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }

    protected abstract int findIndex(String uuid);

    protected abstract void saveResume(Resume resume);

    protected abstract void deleteResume(int index);
}


