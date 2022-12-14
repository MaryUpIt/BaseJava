package com.urise.webapp.storage;

import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.strategy.StreamStorage;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {
    private final Path directory;
    private final StreamStorage streamStorage;

    public PathStorage(String directoryName, StreamStorage streamStorage) {
        directory = Path.of(directoryName);
        Objects.requireNonNull(directory, " directory must not be null");
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directoryName + " isn't directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directoryName + " isn't readable/writeable");
        }
        this.streamStorage = streamStorage;
    }

    @Override
    protected void doSave(Path path, Resume resume) {
        try {
            Files.createFile(path);
        } catch (IOException e) {
            throw new StorageException("IO error: ", e);
        }
        doUpdate(path, resume);
    }

    @Override
    protected void doUpdate(Path path, Resume resume) {
        try {
            streamStorage.doWrite(resume, new BufferedOutputStream(Files.newOutputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error: ", e);
        }
    }

    @Override
    protected void doDelete(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new StorageException("Unable to delete file: ", getPathName(path));
        }
    }

    @Override
    protected Resume doGet(Path path) {
        try {
            return streamStorage.doRead(new BufferedInputStream(Files.newInputStream(path)));
        } catch (IOException e) {
            throw new StorageException("IO error: ", getPathName(path), e);
        }
    }

    @Override
    protected Path findSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path path) {
        // return Files.exists(path);
        return Files.isRegularFile(path);
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> resumesList = new ArrayList<>();
        getPathStream().forEach(path -> resumesList.add(doGet(path)));
        return resumesList;
    }

    @Override
    public int size() {
        return (int) getPathStream().count();
    }

    @Override
    public void clear() {
        getPathStream().forEach(this::doDelete);
    }

    private Stream<Path> getPathStream() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Unable to get list from directory ",e);
        }
    }

    private String getPathName(Path path) {
        return path.getFileName().toString();
    }
}
