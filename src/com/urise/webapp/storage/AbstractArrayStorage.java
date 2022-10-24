package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_LIMIT =10_000;
    protected   Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;
    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, size);
    }
    @Override
    public int size() {
        return size;
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
    public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (index != -1) {
            return storage[index];
        }
        System.out.printf("резюме с идентификатором %s отсутствует в хранилище.\n", uuid);
        return null;
    }

    protected abstract int findIndex(String uuid);

    public void test() {
        storage[0] = new Resume("uuid1");
        storage[1] = new Resume("uuid23");
        storage[2] = new Resume("uuid24");
        storage[3] = new Resume("uuid3");
        storage[4] = new Resume("uuid4");
        storage[5] = new Resume("uuid32");
        storage[6] = new Resume("uuid34");
        storage[7] = new Resume("uuid6");
        storage[8] = new Resume("uuid6");
        storage[9] = new Resume("uuid2");

        size = 10;
    }
}
