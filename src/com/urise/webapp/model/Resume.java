package com.urise.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.ContactType.HOMEPAGE;
import static com.urise.webapp.model.SectionType.*;
import static com.urise.webapp.model.SectionType.EDUCATION;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;
    private String uuid;
    private String fullName;
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);


    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
    }

    public Resume() {
    }

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        Objects.requireNonNull(uuid, "uuid must not be null");
        Objects.requireNonNull(fullName, "fullName must not be null");
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void addContact(ContactType contactType, String contact) {
        contacts.put(contactType, contact);
    }

    public String getContact(ContactType contactType) {
        return contacts.get(contactType);
    }

    public void setSection(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType, section);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public void printResume(Resume resume) {
        System.out.println(resume);
        System.out.println(PHONE.getTitle() + " : " + resume.getContact(PHONE));
        System.out.println(SKYPE.getTitle() + " : " + resume.getContact(SKYPE));
        System.out.println(EMAIL.getTitle() + " :  " + resume.getContact(EMAIL));
        System.out.println(LINKEDIN.getTitle() + " : " + resume.getContact(LINKEDIN));
        System.out.println(GITHUB.getTitle() + " : " + resume.getContact(GITHUB));
        System.out.println(STACKOVERFLOW.getTitle() + " : " + resume.getContact(STACKOVERFLOW));
        System.out.println(HOMEPAGE.getTitle() + " : " + resume.getContact(HOMEPAGE));

        System.out.println(PERSONAL.getTitle() + " : " + resume.getSection(PERSONAL));
        System.out.println(OBJECTIVE.getTitle() + " : " + resume.getSection(OBJECTIVE));
        System.out.println(ACHIEVEMENT.getTitle() + "\n" + resume.getSection(ACHIEVEMENT));
        System.out.println(QUALIFICATIONS.getTitle() + "\n" + resume.getSection(QUALIFICATIONS));
        System.out.println(EXPERIENCE.getTitle() + "\n" + resume.getSection(EXPERIENCE));
        System.out.println(EDUCATION.getTitle() + "\n" + resume.getSection(EDUCATION));
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
        return Objects.equals(uuid, resume.uuid)
                && Objects.equals(fullName, resume.fullName)
                && Objects.equals(contacts, resume.contacts)
                && Objects.equals(sections, resume.sections);

    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, contacts, sections);
    }
}
