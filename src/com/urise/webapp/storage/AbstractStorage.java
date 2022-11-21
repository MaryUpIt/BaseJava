package com.urise.webapp.storage;

import com.urise.webapp.exceptions.ExistStorageException;
import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;

abstract public class AbstractStorage implements Storage {

    public static final Comparator<Resume> NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);
//        (resume1, resume2) -> resume1.getFullName().compareTo(resume2.getFullName());
//        if (EXP or 0) -> resume1.getUuid().compareTo(resume2.getUuid());

    @Override
    final public void save(Resume resume) {
        Object searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
        System.out.println("Resume " + resume + " add in storage.");

    }

    @Override
    final public void update(Resume resume) {
        Object searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
        System.out.println("Update resume: " + resume);

    }

    @Override
    final public void delete(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
        System.out.printf("Resume %s delete from storage.\n", uuid);
    }

    @Override
    final public Resume get(String uuid) {
        Object searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);

    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = getList();
        list.sort(NAME_COMPARATOR);
        return list;

    }

    private Object getExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            throw new NotExistStorageException(uuid);
        }

        return searchKey;
    }

    private Object getNotExistingSearchKey(String uuid) {
        Object searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void doSave(Object searchKey, Resume resume);

    protected abstract void doUpdate(Object searchKey, Resume resume);

    protected abstract void doDelete(Object searchKey);

    protected abstract Resume doGet(Object searchKey);

    protected abstract Object findSearchKey(String uuid);

    protected abstract boolean isExist(Object searchKey);

    protected abstract List<Resume> getList();


}
