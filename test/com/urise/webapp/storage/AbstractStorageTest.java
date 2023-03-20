package com.urise.webapp.storage;

import com.urise.webapp.exceptions.ExistStorageException;
import com.urise.webapp.exceptions.NotExistStorageException;
import com.urise.webapp.exceptions.StorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.util.Config;
import com.urise.webapp.util.DateUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static com.urise.webapp.model.ContactType.*;
import static com.urise.webapp.model.SectionType.*;
import static java.time.Month.*;
import static org.junit.jupiter.api.Assertions.*;


public abstract class AbstractStorageTest {

    protected static final File STORAGE_DIR = Config.getInstance().getStorageDir();
            //new File("./resumes");

    final Storage storage;
    private Resume RESUME_1 = createResume("Anna");
    private Resume RESUME_2 = createResume("Mariya");

    private Resume RESUME_3 = createResume("Dmitriy");
    private Resume RESUME_4 = createResume("Fedor");

    protected AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    public void setUp() {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size() {
        assertSize(3);
    }

    @Test
    public void save() {
        storage.save(RESUME_4);
        assertSize(4);
        assertGet(RESUME_4);
    }

    @Test
    public void saveExist() {
        Assertions.assertThrows(ExistStorageException.class, () -> {
            storage.save(RESUME_3);
        });
    }

    @Test
    public void update() {
        String uuid = RESUME_1.getUuid();
        Resume resume =  createResume(uuid, "Ignat");
        storage.update(resume);
        assertTrue(storage.get(uuid).equals(resume));
        assertNotSame(RESUME_1, storage.get(uuid));
    }

    @Test
    public void updateNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.update(RESUME_4);
        });
    }

    @Test
    public void delete() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(RESUME_2.getUuid());
            assertSize(2);
            assertGet(RESUME_2);
        });
    }

    @Test
    public void deleteNotExist() {
        Assertions.assertThrows(NotExistStorageException.class, () -> {
            storage.delete(RESUME_4.getUuid());
        });
    }

    @Test
    public void get() {
        Resume resume = storage.get(RESUME_1.getUuid());
        assertEquals(RESUME_1, resume);
    }

    @Test
    public void getNotExist() {
        Assertions.assertThrows(StorageException.class, () -> {
            storage.get(RESUME_4.getUuid());
        });
    }

    @Test
    public void clear() {
        storage.clear();
        assertSize(0);
    }

    @Test
    public void getAllSorted() {
        List<Resume> list = storage.getAllSorted();
        assertEquals(3, list.size());
        assertEquals(list, Arrays.asList(RESUME_1, RESUME_3, RESUME_2));
    }

    private void assertSize(int size) {
        assertEquals(size, storage.size());
    }

    private void assertGet(Resume resume) {
        assertEquals(resume, storage.get(resume.getUuid()));
    }

    private Resume createResume(String... arguments) {
        Resume resume = null;
        String fullName = null;
        if (arguments.length == 2) {
            fullName = arguments[1];
            resume = new Resume(arguments[0], fullName);
        } else if (arguments.length == 1) {
            fullName = arguments[0];
            resume = new Resume(fullName);
        } else {
            throw new IllegalArgumentException();
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
                        new Organization.Period("specialist", null,
                                DateUtil.of(2012, SEPTEMBER), DateUtil.of(2017, MAY)),
                        new Organization.Period("magistrate", null,
                                DateUtil.of(2017, SEPTEMBER), DateUtil.of(2019, MAY))),
                new Organization("Courses", "www.courses.com",
                        new Organization.Period("qualification", null,
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