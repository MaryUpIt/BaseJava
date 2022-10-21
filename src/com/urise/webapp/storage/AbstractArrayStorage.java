package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorage implements Storage {
    protected final static int STORAGE_LIMIT =10_000;
    protected   Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int size;

    public int size() {
        return size;
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
