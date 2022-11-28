package com.urise.webapp.main;


import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.*;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestDate {
    public static void main(String[] args) {
        Resume resume = new Resume("John Dou");
        resume.setContacts(PHONE, "+7-926-567-34-74");
        resume.setContacts(GITHUB, "github.com");
        resume.setContacts(LINKEDIN, "LinkedIn");
        resume.setContacts(EMAIL,"email@gmail.com");
        resume.setSections(PERSONAL, new TextSection("analytical mindset, logic, creativity, initiative"));
        List<String>qualifications = new ArrayList<>();
        qualifications.add("Languages:Java, JavaScript, Python, SQL.");
        qualifications.add("Version control: Git.");
        qualifications.add("DB: PostgreSQL.");
        qualifications.add("FrameWorks:  Java 8 (Time API, Streams), Spring (MVC, Security, Data, Clouds, Boot)," +
                " JPA (Hibernate, EclipseLink), Apache Commons, Eclipse SWT, JUnit.");
        resume.setSections(QUALIFICATIONS, new ListSection(qualifications));

        Map<Period, Organization> experience = new HashMap<>();
        experience.put(new Period(LocalDate.of(2017,1,1),LocalDate.of(2020,1,1)),
                new Organization("Yandex", "Java Developer"));
        experience.put(new Period(LocalDate.of(2015,1,1),LocalDate.of(2017,1,1)),
                new Organization("Yandex", "Junior Java Developer"));
        resume.setSections(EXPERIENCE, new OrganizationSections(experience));

        System.out.println(resume);
        System.out.println(PHONE.getTitle() + " :" + resume.getContacts(PHONE));
        System.out.println(GITHUB.getTitle() + " : "+ resume.getContacts(GITHUB));
        System.out.println(QUALIFICATIONS.getTitle() + "\n" + resume.getSection(QUALIFICATIONS));
        System.out.println(EXPERIENCE.getTitle() + "\n" + resume.getSection(EXPERIENCE));



    }


}
