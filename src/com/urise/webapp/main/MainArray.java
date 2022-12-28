package com.urise.webapp.main;

import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.storage.ArrayStorage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class MainArray {

    private final static ArrayStorage ARRAY_STORAGE = new ArrayStorage();

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        Resume resume;
        while (true) {
            System.out.print("Введите одну из команд - (list | size | save uuid fullName | update uuid fullName| delete uuid | get uuid | clear | exit): ");
            String[] params = reader.readLine().trim().toLowerCase().split(" ");
            if (params.length < 1 || params.length > 3) {
                System.out.println("Неверная команда.");
                continue;
            }
            String param = null;
          //  String fullName = null;
            if (params.length > 1) {
                param = params[1].intern();
               // fullName =params[2].intern();
            }
            switch (params[0]) {
                case "list":
                    printAll();
                    break;
                case "size":
                    System.out.println(ARRAY_STORAGE.size());
                    break;
                case "save":
                    resume = new Resume(param);
                    ARRAY_STORAGE.save(resume);
                    break;
                case "update":
                    resume = ARRAY_STORAGE.get(param);
                    resume.addContact(ContactType.PHONE,"+7-926-547-73-46");
                   // resume.setFullName(params[2]);
                    ARRAY_STORAGE.update(resume);
                    break;
                case "delete":
                    ARRAY_STORAGE.delete(param);
                    break;
                case "get":
                    System.out.println(ARRAY_STORAGE.get(param));
                    break;
                case "clear":
                    ARRAY_STORAGE.clear();
                    break;
                case "exit":
                    return;
                default:
                    System.out.println("Неверная команда.");
                    break;
            }
        }
    }

    static void printAll() {
        List<Resume> resumes = ARRAY_STORAGE.getAllSorted();
        System.out.println("----------------------------");
        if (resumes.size() == 0) {
            System.out.println("Empty");
        } else {
            for (Resume resume : resumes) {
                System.out.println(resume);
            }
        }
        System.out.println("----------------------------");
    }
}
