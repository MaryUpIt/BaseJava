package com.urise.webapp.main;


import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;
import static java.time.Month.*;

public class ResumeTestData {
    public static Resume createResume(String uuid, String fullName) {
        Resume resume = new Resume(uuid,fullName);
        //CONTACTS
        resume.setContact(PHONE, "+7-926-567-34-74");
        resume.setContact(GITHUB, "github.com");
        resume.setContact(LINKEDIN, "@LinkedIn");
        resume.setContact(EMAIL, "email@gmail.com");

        resume.setContact(SKYPE, "@skype");
        resume.setContact(HOMEPAGE, "www.homepage.com");
        resume.setContact(STACKOVERFLOW, "stackoverflow");

        //QUALIFICATIONS
        ListSection skills = new ListSection();

        skills.addSection("Languages: Java, JavaScript, Python, SQL.");
        skills.addSection("Version control: Git.");
        skills.addSection("DB: PostgreSQL, MySql.");
        skills.addSection("FrameWorks:  Java 8 (Time API, Streams), Spring (MVC, Security, Data, Clouds, Boot)," +
                " JPA (Hibernate, EclipseLink), Apache Commons, Eclipse SWT, JUnit.");

        //ACHIEVEMENT
        ListSection achievements = new ListSection();

        achievements.addSection("Team organization and successful implementation of Java projects for customers:");
        achievements.addSection("Creating a JavaEE frameworks for applications");
        achievements.addSection("Development and continuous integration River BPM ERP system." +
                " Integration with 1C, Bonita BPM, CMIS, LDAP.");
        achievements.addSection("Creating a JavaEE framework for fault-tolerant interaction of loosely coupled services");
        achievements.addSection("Implementation of two-factor authentication for  online projects");

        //EXPERIENCE

        Organization fistProduction = new Organization("FistProduction", "www.firstproduction.com");
        LocalDate dateFrom = DateUtil.of(2017, JANUARY);
        LocalDate dateTo = DateUtil.of(2018, MAY);
        String responsibilities = """
                    Work as part of a software development team
                    Write code per app specifications
                    Test to ensure designs are in compliance with specifications
                    Analyze user requirements to determine how to translate into Java
                    Debug and resolving technical issues
                    Make recommendations to existing job infrastructure
                    Continually engage in professional development
                    Develop documentation to track
                """;

        fistProduction.addPosition(new Organization.Period("Java Junior", responsibilities, dateFrom, dateTo));
        dateFrom = dateTo;
        dateTo = DateUtil.of(2020, OCTOBER);
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
        fistProduction.addPosition(new Organization.Period("Java Developer", responsibilities, dateFrom, dateTo));
        Organization secondProduction = new Organization("SecondProduction", "www.secondproduction.com");
        dateFrom = dateTo;
        dateTo = DateUtil.NOW;
        secondProduction.addPosition(new Organization.Period("Java Developer", responsibilities, dateFrom, dateTo));

        OrganizationSection experience = new OrganizationSection(fistProduction,secondProduction);


//    EDUCATION("Образование")
       // List<Organization> institutions = new ArrayList<>();

        List<Organization.Period> university = new ArrayList<>();
        dateFrom = DateUtil.of(2014, SEPTEMBER);
        dateTo = DateUtil.of(2015, JUNE);
        university.add(new Organization.Period("first course", "", dateFrom, dateTo));
        dateFrom = DateUtil.of(2015, SEPTEMBER);
        dateTo = DateUtil.of(2016, JUNE);
        university.add(new Organization.Period("second course", "", dateFrom, dateTo));
        dateFrom = DateUtil.of(2016, SEPTEMBER);
        dateTo = DateUtil.of(2017, JUNE);
        university.add(new Organization.Period("qualification course", "", dateFrom, dateTo));
      //  institutions.add(new Organization("university", "www.university.com", university));
        OrganizationSection education = new OrganizationSection(new Organization("university", "www.university.com", university));

        resume.setSection(PERSONAL, new TextSection("analytical mindset, logic, creativity, initiative"));
        resume.setSection(OBJECTIVE, new TextSection("Java Developer"));
        resume.setSection(ACHIEVEMENT, achievements);
        resume.setSection(QUALIFICATIONS, skills);
        resume.setSection(EXPERIENCE, experience);
        resume.setSection(EDUCATION, education);

        return resume;
    }
    private static void printResume (Resume resume){
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

    public static void main(String[] args) {
        printResume(createResume("uuid4576", "John Dou"));

    }
}
