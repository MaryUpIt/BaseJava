package com.urise.webapp.storage;

import com.urise.webapp.exceptions.ExistStorageException;
import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.model.Resume;

abstract public class AbstractStorage implements Storage {

    @Override
    final public int size() {
        return getSize();
    }

    @Override
    final public void save(Resume resume) {
        if (findIndex(resume.getUuid()) >= 0) {
            throw new ExistStorageException(resume.getUuid());
        }
        store(resume);
        System.out.println("Resume " + resume + " add in storage.");

    }

    @Override
    final public void update(Resume resume) {
        int index = findIndex(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }

        updateResume(index,resume);
        System.out.println("Update resume: " + resume);

    }

    @Override
    final public void delete(String uuid) {
        int index = findIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        removeResume(index);
        System.out.printf("Resume %s delete from storage.\n", uuid);
    }


    @Override
    final public Resume get(String uuid) {
        int index = findIndex(uuid);
        if (findIndex(uuid) < 0) {
            throw new NotExistStorageException(uuid);
        }
        return getResume(index);
    }


    protected abstract int getSize();
    protected abstract void store(Resume resume);

    protected abstract void updateResume(int index, Resume resume);

    protected abstract void removeResume(int index);

    protected abstract Resume getResume(int index);

    protected abstract int findIndex(String uuid);


}
