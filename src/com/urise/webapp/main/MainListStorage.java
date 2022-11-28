package com.urise.webapp.main;

import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ListStorage;

public class MainListStorage {
    static final ListStorage LIST_STORAGE = new ListStorage();

    public static void main(String[] args) {
        Resume resume1 = new Resume("uuid1", "Anna");
        Resume resume2 = new Resume("uuid2", "Fedor");
        Resume resume3 = new Resume("uuid3", "Boris");

        LIST_STORAGE.save(resume1);
        LIST_STORAGE.save(resume2);
        LIST_STORAGE.save(resume3);
        //printAll();

        System.out.println(LIST_STORAGE.get("uuid2"));
        LIST_STORAGE.delete("uuid2");
        System.out.println(LIST_STORAGE.size());
        printAll();
    }



    static void printAll() {
        System.out.println("\nStorage: ");
        for (Resume resume : LIST_STORAGE.getAllSorted()) {
            System.out.println(resume);
        }
    }
}
