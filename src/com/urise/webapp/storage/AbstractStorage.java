package com.urise.webapp.storage;

import com.urise.webapp.exceptions.ExistStorageException;
import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

abstract public class AbstractStorage<SearchKey> implements Storage {
    //protected final Logger log = Logger.getLogger(getClass().getName());
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public static final Comparator<Resume> NAME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    @Override
    final public void save(Resume resume) {
        LOG.info("SAVE: " + resume);
        SearchKey searchKey = getNotExistingSearchKey(resume.getUuid());
        doSave(searchKey, resume);
        System.out.println("Resume " + resume + " add in storage.");
    }

    @Override
    final public void update(Resume resume) {
        LOG.info("UPDATE: " + resume);
        SearchKey searchKey = getExistingSearchKey(resume.getUuid());
        doUpdate(searchKey, resume);
        System.out.println("Update resume: " + resume);
    }

    @Override
    final public void delete(String uuid) {
        LOG.info("DELETE: " + uuid);
        SearchKey searchKey = getExistingSearchKey(uuid);
        doDelete(searchKey);
        System.out.printf("Resume %s delete from storage.\n", uuid);
    }

    @Override
    final public Resume get(String uuid) {
        LOG.info("GET: " + uuid);
        SearchKey searchKey = getExistingSearchKey(uuid);
        return doGet(searchKey);
    }

    @Override
    final public List<Resume> getAllSorted() {
        LOG.info("GET ALL SORTED");
        List<Resume> list = doGetAll();
        list.sort(NAME_COMPARATOR);
        return list;
    }

    private SearchKey getExistingSearchKey(String uuid) {
        SearchKey searchKey = findSearchKey(uuid);
        if (!isExist(searchKey)) {
            LOG.warning("Resume " + uuid +" not exist");
            throw new NotExistStorageException(uuid);
        }
        return searchKey;
    }

    private SearchKey getNotExistingSearchKey(String uuid) {
        SearchKey searchKey = findSearchKey(uuid);
        if (isExist(searchKey)) {
            LOG.warning("Resume " + uuid +" already exist");
            throw new ExistStorageException(uuid);
        }
        return searchKey;
    }

    protected abstract void doSave(SearchKey searchKey, Resume resume);

    protected abstract void doUpdate(SearchKey searchKey, Resume resume);

    protected abstract void doDelete(SearchKey searchKey);

    protected abstract Resume doGet(SearchKey searchKey);

    protected abstract SearchKey findSearchKey(String uuid);

    protected abstract boolean isExist(SearchKey searchKey);

    protected abstract List<Resume> doGetAll();
}
