package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.StreamStorage;

import java.nio.file.Path;
import java.util.List;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamStorage streamStorage;

    public PathStorage(Path directory, StreamStorage streamStorage) {

        this.directory = directory;
        this.streamStorage = streamStorage;
    }

    @Override
    protected void doSave(Path path, Resume resume) {

    }

    @Override
    protected void doUpdate(Path path, Resume resume) {

    }

    @Override
    protected void doDelete(Path path) {

    }

    @Override
    protected Resume doGet(Path path) {
        return null;
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return null;
    }

    @Override
    protected boolean isExist(Path path) {
        return false;
    }

    @Override
    protected List<Resume> doGetAll() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void clear() {

    }
}
