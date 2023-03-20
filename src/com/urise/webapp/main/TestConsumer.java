package com.urise.webapp.main;

import com.urise.webapp.model.*;
import com.urise.webapp.util.DateUtil;

import java.util.function.Consumer;
import java.util.function.Function;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;
import static java.time.Month.*;

public class TestConsumer {
    public static void main(String[] args) {
        Consumer<Resume> consumer = (resume) ->resume.printResume();
        consumer.accept(createResume("Anna"));

        ((Consumer<Resume>) resume -> resume.printResume()).accept(createResume("Elena"));



        System.out.println(consumer.getClass().getName());



        Function<String, Integer> length = (string) -> {
            int a = string.length();
            return a++;
        };
        length.apply("Function");

    }

    private static Resume createResume(String... arguments) {
        Resume resume = null;
        String fullName = null;
        if (arguments.length == 2) {
            fullName = arguments[1];
            resume = new Resume(arguments[0], fullName);
        } else if (arguments.length == 1) {
            fullName = arguments[0];
            resume = new Resume(fullName);
        } else {
            throw new IllegalArgumentException("Error");
        }

        resume.setContact(PHONE, "+7-926-567-34-74");
        resume.setContact(GITHUB, "github.com/" + fullName);
        resume.setContact(LINKEDIN, "@LinkedIn/" + fullName);
        resume.setContact(EMAIL, fullName + "@gmail.com");
        resume.setContact(SKYPE, fullName + "@skype");
        resume.setContact(HOMEPAGE, String.format("www.%s.com", fullName));
        resume.setContact(STACKOVERFLOW, "stackoverflow/" + fullName);

        resume.setSection(PERSONAL, new TextSection("personal characteristics"));
        resume.setSection(OBJECTIVE, new TextSection("position"));
        resume.setSection(QUALIFICATIONS, new ListSection("Languages skills", "Frameworks skills", "DB skills"));
        resume.setSection(ACHIEVEMENTS, new ListSection("achievement 1", "achievement 2"));
        resume.setSection(EDUCATION, new OrganizationSection(
                new Organization("University", "www.university.com",
                        new Organization.Period("specialist", "",
                                DateUtil.of(2012, SEPTEMBER), DateUtil.of(2017, MAY)),
                        new Organization.Period("magistrate", "",
                                DateUtil.of(2017, SEPTEMBER), DateUtil.of(2019, MAY))),
                new Organization("Courses", "www.courses.com",
                        new Organization.Period("qualification", "",
                                DateUtil.of(2019, APRIL), DateUtil.of(2019, AUGUST)))));
        resume.setSection(EXPERIENCE, new OrganizationSection(
                new Organization("FirstCompany", "www.firstCompany.com",
                        new Organization.Period("junior", "help for production",
                                DateUtil.of(2019, SEPTEMBER), DateUtil.of(2020, MARCH)),
                        new Organization.Period("developer", "create programs",
                                DateUtil.of(2020, JUNE), DateUtil.of(2021, OCTOBER))),
                new Organization("SecondCompany", "www.secondCompany.com",
                        new Organization.Period("senior", "education new specialists for company",
                                DateUtil.of(2021, NOVEMBER), DateUtil.NOW))));

        return resume;
    }
}
