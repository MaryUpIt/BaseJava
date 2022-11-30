package com.urise.webapp.model;

import java.util.EnumMap;
import java.util.UUID;

public class Resume implements Comparable<Resume> {
    private final String uuid;
    private String fullName;
    private EnumMap<ContactType, String> contacts;
    private EnumMap<SectionType,AbstractSection> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
        contacts = new EnumMap<>(ContactType.class);
        sections = new EnumMap<>(SectionType.class);
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

    public void setContacts(ContactType contactType, String contact) {
        contacts.put(contactType,contact);
    }

    public String getContacts(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setSections(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType,section);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }
}
