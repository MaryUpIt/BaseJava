package com.urise.webapp.storage;

import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.StreamStorage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class FileStorage extends AbstractStorage<File> {
    private final File directory;
    private final StreamStorage streamStorage;

    protected FileStorage(File directory, StreamStorage streamStorage) {
        Objects.requireNonNull(directory, "directory must not be null");
        this.streamStorage = streamStorage;
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "isn't directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + "isn't readable/writeable");
        }
        this.directory = directory;
    }


    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
        doUpdate(file, resume);
    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            streamStorage.doWrite(resume, new BufferedOutputStream(new FileOutputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected void doDelete(File file) {
        if (!file.delete()) {
            throw new StorageException("Unable to delete file: ", file.getAbsolutePath());
        }
    }

    @Override
    protected Resume doGet(File file) {
        try {
            return streamStorage.doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(), e);
        }
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> resumes = new ArrayList<>();
        File[] storage = getStorage();
        for (File file : storage) {
            resumes.add(doGet(file));
        }
        return resumes;
    }

    @Override
    public int size() {
        File[] storage = getStorage();
        return storage.length;
    }

    @Override
    public void clear() {
        File[] storage = getStorage();
        for (File file : storage) {
            doDelete(file);
        }
    }

    private File[] getStorage() {
        File[] storage = directory.listFiles();
        if (storage == null) {
            throw new StorageException("Unable to get list from dir : ", directory.getName());
        }
        return storage;
    }
}
