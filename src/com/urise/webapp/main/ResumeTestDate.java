package com.urise.webapp.main;


import com.urise.webapp.model.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;

public class ResumeTestDate {
    public static void main(String[] args) {
        Resume resume = new Resume("John Dou");
        resume.setContacts(PHONE, "+7-926-567-34-74");
        resume.setContacts(GITHUB, "github.com");
        resume.setContacts(LINKEDIN, "LinkedIn");
        resume.setContacts(EMAIL, "email@gmail.com");
        resume.setSections(PERSONAL, new TextSection("analytical mindset, logic, creativity, initiative"));

        List<String> qualifications = new ArrayList<>();
        qualifications.add("Languages:Java, JavaScript, Python, SQL.");
        qualifications.add("Version control: Git.");
        ListSection skills = new ListSection(qualifications);
        skills.addSection("DB: PostgreSQL.");
        skills.addSection("FrameWorks:  Java 8 (Time API, Streams), Spring (MVC, Security, Data, Clouds, Boot)," +
                " JPA (Hibernate, EclipseLink), Apache Commons, Eclipse SWT, JUnit.");
        resume.setSections(QUALIFICATIONS, skills);


        Organization organization = new Organization("FistProduction", "www.firstproduction.com");
        LocalDate dateFrom = LocalDate.of(2017, 7, 1);
        LocalDate dateTo = LocalDate.of(2018, 12, 26);
        String responsibilities = """
                Work as part of a software development team
                Write code per app specifications
                Test to ensure designs are in compliance with specifications
                Analyze user requirements to determine how to translate into Java
                Debug and resolving technical issues
                Make recommendations to existing job infrastructure
                Continually engage in professional development
                Develop documentation to track""";

        organization.addPosition(new Period("Java Junior", responsibilities, dateFrom, dateTo));
        dateFrom = dateTo;
        dateTo = LocalDate.of(2020, 10, 28);
        responsibilities = """
                Designing, implementing, and maintaining Java applications that are often high-volume and low-latency, required for mission-critical systems
                Delivering high availability and performance
                Contributing in all phases of the development lifecycle
                Writing well-designed, efficient, and testable code
                Conducting software analysis, programming, testing, and debugging
                Managing Java and Java EE application development
                Ensuring designs comply with specifications
                Preparing and producing releases of software components
                Transforming requirements into stipulations
                Support continuous improvement
                """;
        organization.addPosition(new Period("Java Developer", responsibilities, dateFrom, dateTo));
       // System.out.println(organization);

        OrganizationSection organizations = new OrganizationSection();
        organizations.addOrganization(organization);
      //  System.out.println(organizations);


        resume.setSections(EXPERIENCE, organizations);

        System.out.println(resume);
        System.out.println(PHONE.getTitle() + " :" + resume.getContacts(PHONE));
        System.out.println(GITHUB.getTitle() + " : " + resume.getContacts(GITHUB));
        System.out.println(QUALIFICATIONS.getTitle() + "\n" + resume.getSection(QUALIFICATIONS));
        System.out.println(EXPERIENCE.getTitle() + "\n" + resume.getSection(EXPERIENCE));


    }


}
