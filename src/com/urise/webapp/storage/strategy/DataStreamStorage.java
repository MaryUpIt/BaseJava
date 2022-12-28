package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataStreamStorage implements StreamStorage {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream stream = new DataOutputStream(outputStream)) {
            stream.writeUTF(resume.getUuid());
            stream.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            stream.writeInt(contacts.size());

            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
                stream.writeUTF(entry.getKey().name());
                stream.writeUTF(entry.getValue());
            }
            Map<SectionType, AbstractSection> sections = resume.getSections();
            stream.writeInt(sections.size());
            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
                SectionType sectionType = entry.getKey();
                stream.writeUTF(sectionType.name());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> stream.writeUTF(entry.getValue().toString());
                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSection((ListSection) entry.getValue(), stream);
                    case EDUCATION, EXPERIENCE ->
                            writeOrganizationSection((OrganizationSection) entry.getValue(), stream);
                }
            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream input = new DataInputStream(inputStream)) {
            String uuid = input.readUTF();
            String fullName = input.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int contactSize = input.readInt();
            for (int i = 0; i < contactSize; i++) {
                ContactType contactType = ContactType.valueOf(input.readUTF());
                String contact = input.readUTF();
                resume.addContact(contactType, contact);
            }
            int sectionSize = input.readInt();
            for (int i = 0; i < sectionSize; i++) {
                SectionType sectionType = SectionType.valueOf(input.readUTF());
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> {
                        resume.setSection(sectionType, new TextSection(input.readUTF()));
                    }
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        int listSectionSize = input.readInt();
                        List<String> list = new ArrayList<>();
                        for (int j = 0; j < listSectionSize; j++) {
                            list.add(input.readUTF());
                        }
                        resume.setSection(sectionType, new ListSection(list));
                    }
                    //   writeListSection((ListSection) entry.getValue(), stream);
                    case EDUCATION, EXPERIENCE -> {
                        int organizationSectionSize = input.readInt();
                        List<Organization> organizations = new ArrayList<>();
                        for (int j = 0; j < organizationSectionSize; j++) {
                            Organization organization = new Organization(input.readUTF(), input.readUTF());
                            int periodSize = input.readInt();
                            List<Organization.Period> periods = new ArrayList<>();
                            for (int k = 0; k < periodSize; k++) {
                                Organization.Period period = new Organization.Period(input.readUTF(), input.readUTF(),
                                        LocalDate.parse(input.readUTF()), LocalDate.parse(input.readUTF()));
                                periods.add(period);
                            }
                            organizations.add(organization);
                        }
                        resume.setSection(sectionType, new OrganizationSection(organizations));
                    }
                }
            }


            return resume;
        }
    }

    private void writeOrganizationSection(OrganizationSection organizationSection, DataOutputStream stream) throws IOException {
        stream.writeInt(organizationSection.getOrganizations().size());
        for (Organization organization : organizationSection.getOrganizations()) {
            stream.writeUTF(organization.getTitle());
            stream.writeUTF(organization.getWebsite());
            stream.writeInt(organization.getPeriods().size());
            for (Organization.Period period : organization.getPeriods()) {
                stream.writeUTF(period.getPosition());
                stream.writeUTF(period.getResponsibilities());
                stream.writeUTF(period.getDateFrom().toString());
                stream.writeUTF(period.getDateTo().toString());
            }
        }
    }

    private void writeListSection(ListSection listSection, DataOutputStream stream) throws IOException {
        int listSectionSize = listSection.getContent().size();
        stream.writeInt(listSectionSize);
        for (String content : listSection.getContent()) {
            stream.writeUTF(content);
        }
    }
}
