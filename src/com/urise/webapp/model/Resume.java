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

    public Map<ContactType, String> getContacts() {
        return contacts;
    }

    public Map<SectionType, AbstractSection> getSections() {
        return sections;
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

    public void addSection(SectionType sectionType, AbstractSection section) {
        sections.put(sectionType, section);
    }

    public AbstractSection getSection(SectionType sectionType) {
        return sections.get(sectionType);
    }

    public String printResume(Resume resume) {
        return resume.getUuid() + " : " + resume.getFullName()
                + "\n" + PHONE.getTitle() + " : " + resume.getContact(PHONE)
                + "\n" + SKYPE.getTitle() + " : " + resume.getContact(SKYPE)
                + "\n" + EMAIL.getTitle() + " :  " + resume.getContact(EMAIL)
                + "\n" + LINKEDIN.getTitle() + " : " + resume.getContact(LINKEDIN)
                + "\n" + GITHUB.getTitle() + " : " + resume.getContact(GITHUB)
                + "\n" + STACKOVERFLOW.getTitle() + " : " + resume.getContact(STACKOVERFLOW)
                + "\n" + HOMEPAGE.getTitle() + " : " + resume.getContact(HOMEPAGE)
                + "\n" + PERSONAL.getTitle() + " : " + resume.getSection(PERSONAL)
                + "\n" + OBJECTIVE.getTitle() + " : " + resume.getSection(OBJECTIVE)
                + "\n" + ACHIEVEMENT.getTitle() + "\n" + resume.getSection(ACHIEVEMENT)
                + "\n" + QUALIFICATIONS.getTitle() + "\n" + resume.getSection(QUALIFICATIONS)
                + "\n" + EXPERIENCE.getTitle() + "\n" + resume.getSection(EXPERIENCE)
                + "\n" + EDUCATION.getTitle() + "\n" + resume.getSection(EDUCATION);
    }

    @Override
    public String toString() {
        return uuid + " : " + fullName;
//                + "\n" + PHONE.getTitle() + " : " + this.getContact(PHONE)
//                + "\n" + SKYPE.getTitle() + " : " + this.getContact(SKYPE)
//                + "\n" + EMAIL.getTitle() + " :  " + this.getContact(EMAIL)
//                + "\n" + LINKEDIN.getTitle() + " : " + this.getContact(LINKEDIN)
//                + "\n" + GITHUB.getTitle() + " : " + this.getContact(GITHUB)
//                + "\n" + STACKOVERFLOW.getTitle() + " : " + this.getContact(STACKOVERFLOW)
//                + "\n" + HOMEPAGE.getTitle() + " : " + this.getContact(HOMEPAGE)
//                + "\n" + PERSONAL.getTitle() + " : " + this.getSection(PERSONAL)
//                + "\n" + OBJECTIVE.getTitle() + " : " + this.getSection(OBJECTIVE)
//                + "\n" + ACHIEVEMENT.getTitle() + "\n" + this.getSection(ACHIEVEMENT)
//                + "\n" + QUALIFICATIONS.getTitle() + "\n" + this.getSection(QUALIFICATIONS)
//                + "\n" + EXPERIENCE.getTitle() + "\n" + this.getSection(EXPERIENCE)
//                + "\n" + EDUCATION.getTitle() + "\n" + this.getSection(EDUCATION);
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
