package com.urise.webapp.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.Objects;
import java.util.UUID;

public class Resume implements Comparable<Resume>, Serializable {
    private static final  long  serialVersionUID = 1L;
    private final String uuid;
    private final String fullName;
    private final EnumMap<ContactType, String> contacts;
    private final EnumMap<SectionType,AbstractSection> sections;

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
        contacts = new EnumMap<>(ContactType.class);
        sections = new EnumMap<>(SectionType.class);
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setContact(ContactType contactType, String contact) {
        contacts.put(contactType,contact);
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setSection(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType,section);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    @Override
    public String toString() {
        return uuid + " : " + fullName;
    }



    @Override
    public int compareTo(Resume resume) {
        return uuid.compareTo(resume.uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return Objects.equals(uuid, resume.uuid) && Objects.equals(fullName, resume.fullName)
                && Objects.equals(contacts, resume.contacts) && Objects.equals(sections, resume.sections);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
