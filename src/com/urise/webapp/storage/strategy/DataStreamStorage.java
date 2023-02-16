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

            writeCollection(output, resume.getContacts().entrySet(),  contact -> {
                output.writeUTF(contact.getKey().name());
                output.writeUTF(contact.getValue());
            });

            writeCollection(output, resume.getSections().keySet(),  sectionType -> {
                output.writeUTF(sectionType.name());
                AbstractSection section = resume.getSection(sectionType);
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE ->
                            output.writeUTF(((TextSection) section).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeCollection( output, ((ListSection) section).getContent(),output::writeUTF);
                    case EDUCATION, EXPERIENCE ->
                            writeCollection(output, ((OrganizationSection) section).getContent(), organization -> {
                                output.writeUTF(organization.getTitle());
                                output.writeUTF(organization.getWebsite());
                                writeCollection(output, organization.getPeriods(), period -> {
                                    output.writeUTF(period.getPosition());
                                    output.writeUTF(period.getResponsibilities());
                                    output.writeUTF(period.getDateFrom().toString());
                                    output.writeUTF(period.getDateTo().toString());
                                });
                            });
                }
            });
        }
    }

    @Override
    public Resume doRead(InputStream inputStream) throws IOException {
        try (DataInputStream input = new DataInputStream(inputStream)) {
            String uuid = input.readUTF();
            String fullName = input.readUTF();
            Resume resume = new Resume(uuid, fullName);
            readCollection(input, () -> resume.addContact(ContactType.valueOf(input.readUTF()), input.readUTF()));

            readCollection(input, () -> {
                SectionType section = SectionType.valueOf(input.readUTF());
                switch (section) {
                    case PERSONAL, OBJECTIVE -> resume.addSection(section, new TextSection(input.readUTF()));
                    case ACHIEVEMENT, QUALIFICATIONS -> {
                        ListSection list = new ListSection();
                        readCollection(input, () ->
                                list.addSection(input.readUTF()));
                        resume.addSection(section, list);
                    }
                    case EDUCATION, EXPERIENCE -> {
                        OrganizationSection organizations = new OrganizationSection();
                        readCollection(input, () -> {
                            Organization organization = new Organization(input.readUTF(), input.readUTF());
                            readCollection(input, () -> {
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

    private <T> void writeCollection( DataOutputStream output, Collection<T> collection, ConsumerWriter<T> consumer) throws IOException {
        output.writeInt(collection.size());
        for (T item : collection) {
            consumer.write(item);
        }
    }

    private void readCollection(DataInputStream input, ConsumerReader consumer) throws IOException {
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
