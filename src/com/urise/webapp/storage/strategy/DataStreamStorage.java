package com.urise.webapp.storage.strategy;

import com.urise.webapp.model.*;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DataStreamStorage implements StreamStorage {
    @Override
    public void doWrite(Resume resume, OutputStream outputStream) throws IOException {
        try (DataOutputStream output = new DataOutputStream(outputStream)) {
            output.writeUTF(resume.getUuid());
            output.writeUTF(resume.getFullName());

            writeCollection(output, resume.getContacts().entrySet(), contact -> {
                output.writeUTF(contact.getKey().name());
                output.writeUTF(contact.getValue());
            });

            writeCollection(output, resume.getSections().keySet(), sectionType -> {
                output.writeUTF(sectionType.name());
                AbstractSection section = resume.getSection(sectionType);
                switch (sectionType) {
                    case PERSONAL, OBJECTIVE -> output.writeUTF(((TextSection) section).getContent());
                    case ACHIEVEMENT, QUALIFICATIONS ->
                            writeCollection(output, ((ListSection) section).getContent(), output::writeUTF);
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
                resume.addSection(section, readSection(input, section));
            });
            return resume;
        }
    }

    private AbstractSection readSection(DataInputStream input, SectionType sectionType) throws IOException {
        switch (sectionType) {
            case PERSONAL, OBJECTIVE -> {
                return new TextSection(input.readUTF());
            }
            case ACHIEVEMENT, QUALIFICATIONS -> {
                return new ListSection(readList(input, input::readUTF));
            }
            case EDUCATION, EXPERIENCE -> {
                return new OrganizationSection(readList(input, () ->
                        new Organization(input.readUTF(), input.readUTF(), readList(input, () ->
                                new Organization.Period(input.readUTF(), input.readUTF(),
                                        LocalDate.parse(input.readUTF()), LocalDate.parse(input.readUTF()))))));
            }
            default -> throw new IllegalStateException();
        }
    }

    private <T> void writeCollection(DataOutputStream output, Collection<T> collection, ConsumerWriter<T> consumer) throws IOException {
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

    private <T> List<T> readList(DataInputStream input, CustomSupplier<T> supplier) throws IOException {
        int size = input.readInt();
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(supplier.get());
        }
        return list;
    }


    private interface ConsumerWriter<T> {

        void write(T item) throws IOException;
    }

    private interface ConsumerReader {

        void read() throws IOException;
    }

    private interface CustomSupplier<T> {
        T get() throws IOException;
    }
}
