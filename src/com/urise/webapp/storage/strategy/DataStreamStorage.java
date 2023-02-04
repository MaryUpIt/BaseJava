package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.Collection;

public class DataStreamStorage implements StreamStorage {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream output = new DataOutputStream(outputStream)) {
            output.writeUTF(resume.getUuid());
            output.writeUTF(resume.getFullName());

            writeWithException(resume.getContacts().entrySet(), output, contact -> {
                output.writeUTF(contact.getKey().name());
                output.writeUTF(contact.getValue());
            });

            writeWithException(resume.getSections().keySet(), output, section -> {
                output.writeUTF(section.name());
                switch (section) {
                    case PERSONAL, OBJECTIVE ->
                            output.writeUTF(((TextSection) resume.getSection(section)).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeWithException(((ListSection) resume.getSection(section)).getContent(), output, listSection -> {
                                output.writeUTF(listSection);
                            });
                    case EDUCATION, EXPERIENCE ->
                            writeWithException(((OrganizationSection) resume.getSection(section)).getContent(), output, organization -> {
                                output.writeUTF(organization.getTitle());
                                output.writeUTF(organization.getWebsite());
                                writeWithException(organization.getPeriods(), output, period -> {
                                    output.writeUTF(period.getPosition());
                                    output.writeUTF(period.getResponsibilities());
                                    output.writeUTF(period.getDateFrom().toString());
                                    output.writeUTF(period.getDateTo().toString());
                                });
                            });
                }
            });
//            Map<ContactType, String> contacts = resume.getContacts();
//            output.writeInt(contacts.size());
//            for (Map.Entry<ContactType, String> entry : contacts.entrySet()) {
//                output.writeUTF(entry.getKey().name());
//                output.writeUTF(entry.getValue());
//            }

//            Map<SectionType, AbstractSection> sections = resume.getSections();
//            output.writeInt(sections.size());
//            for (Map.Entry<SectionType, AbstractSection> entry : sections.entrySet()) {
//                SectionType sectionType = entry.getKey();
//                output.writeUTF(sectionType.name());
//                switch (sectionType) {
//                    case PERSONAL, OBJECTIVE -> output.writeUTF(((TextSection)entry.getValue()).getContent());
//                    case ACHIEVEMENT, QUALIFICATIONS -> writeListSection((ListSection) entry.getValue(), output);
//                    case EDUCATION, EXPERIENCE -> writeOrganizationSection((OrganizationSection) entry.getValue(), output);
//                }
//            }
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream input = new DataInputStream(inputStream)) {
            String uuid = input.readUTF();
            String fullName = input.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readWithException(input, () -> {
                resume.addContact(ContactType.valueOf(input.readUTF()), input.readUTF());
            });

            readWithException(input, () -> {
                SectionType section = SectionType.valueOf(input.readUTF());
                switch (section) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(section, new TextSection(input.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection list = new ListSection();
                        readWithException(input, () ->
                                list.addSection(input.readUTF()));
                        resume.addSection(section, list);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        OrganizationSection organizations = new OrganizationSection();
                        readWithException(input, () -> {
                            Organization organization = new Organization(input.readUTF(), input.readUTF());
                            readWithException(input, () -> {
                                organization.addPeriod(new Organization.Period(input.readUTF(), input.readUTF(),
                                        LocalDate.parse(input.readUTF()),LocalDate.parse(input.readUTF())));

                            });
                            organizations.addOrganization(organization);
                        });
                        resume.addSection(section, organizations);
                    }
                }
            });
            return resume;
        }
    }

    private void writeListSection(ListSection listSection, DataOutputStream stream) throws IOException {
        int listSectionSize = listSection.getContent().size();
        stream.writeInt(listSectionSize);
        for (String content : listSection.getContent()) {
            stream.writeUTF(content);
        }
    }

    private <T> void writeWithException(Collection<T> collection, DataOutputStream output, ConsumerWriter<T> consumer) throws IOException {
        output.writeInt(collection.size());
        for (T item : collection) {
            consumer.write(item);
        }
    }

    private void readWithException(DataInputStream input, ConsumerReader consumer) throws IOException {
        int size = input.readInt();
        for (int i = 0; i < size; i++) {
            consumer.read();
        }
    }

    private interface ConsumerWriter<T> {
        void write(T item) throws IOException;
    }

    private interface ConsumerReader {
        void read() throws IOException;
    }
}
