package com.urise.webapp.model;

import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private String fullName;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
//        Objects.requireNonNull(uuid, "uuid must be not null");
//        Objects.requireNonNull(fullName, "uuid must be not null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public String toString() {
        return uuid + " : " + fullName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Resume resume = (Resume) obj;
        return uuid.equals(resume.uuid) && fullName.equals(resume.fullName);
    }


    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public int compareTo(Resume resume) {
        return uuid.compareTo(resume.uuid);
    }
}
