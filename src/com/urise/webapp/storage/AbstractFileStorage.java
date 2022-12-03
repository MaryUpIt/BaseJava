package com.urise.webapp.storage;

import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File>{
    private File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory must not be null");
        if (!directory.isDirectory()){
            throw new IllegalArgumentException(directory.getAbsolutePath() + "isn't directory");
        }
        if (!directory.canRead() ||!directory.canWrite()){
            throw new IllegalArgumentException(directory.getAbsolutePath() + "isn't readable/writeable");
        }
        this.directory = directory;
    }
    @Override
    protected void doSave(File file, Resume resume) {
        try {
            file.createNewFile();
            doWrite(resume,file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(),e);
        }


    }

    @Override
    protected void doUpdate(File file, Resume resume) {
        try {
            doWrite(resume,file);
        } catch (IOException e) {
            throw new StorageException("IO error", file.getName(),e);
        }

    }

    @Override
    protected void doDelete(File file) {
        file.delete();
    }

    @Override
    protected Resume doGet(File file) {
        return doRead(file);
    }

    @Override
    protected File findSearchKey(String uuid) {
        return new File(directory,uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }

    @Override
    protected List<Resume> doGetAll() {
        List<Resume> resumes = new ArrayList<>();
        File[] files = directory.listFiles();
        for(File file : files){
            resumes.add(doRead(file));
        }
        return resumes;
    }

    @Override
    public int size() {
        return directory.listFiles().length;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        for(File file : files){
           file.delete();
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    protected abstract Resume doRead(File file);

}
