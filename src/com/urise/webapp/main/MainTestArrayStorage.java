package com.urise.webapp.main;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;

public class MainTestArrayStorage {
    static final ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume("uuid1", "Anna");
        Resume resume2 = new Resume("uuid2", "Fedor");
        Resume resume3 = new Resume("uuid3", "Boris");
//        System.out.println(resume1.compareTo(resume2));
//        System.out.println(resume2.compareTo(resume1));
//        System.out.println(resume1.compareTo(resume1));

        ARRAY_STORAGE.save(resume1);
        ARRAY_STORAGE.save(resume2);
        ARRAY_STORAGE.save(resume3);

        System.out.println("Get resume1: " + ARRAY_STORAGE.get(resume1.getUuid()));
        System.out.println("Size: " + ARRAY_STORAGE.size());

        System.out.println("Get dummy: " + ARRAY_STORAGE.get("dummy"));

        printAll();
        ARRAY_STORAGE.delete(resume1.getUuid());
        printAll();
        ARRAY_STORAGE.clear();
        printAll();

        System.out.println("Size: " + ARRAY_STORAGE.size());
    }

    static void printAll() {
        System.out.println("\nStorage: ");
        for (Resume resume : ARRAY_STORAGE.getAllSorted()) {
            System.out.println(resume);
        }
    }
}
