package com.urise.webapp.main;

import java.io.File;
import java.io.IOException;

public class MainFile {
    public static void main(String[] args) throws IOException {

        File directory = new File("..\\basejava");
        System.out.println(directory.getCanonicalFile());
        System.out.println(directory.getName());
        System.out.println(directory.isDirectory());
        printFilesTree(directory);

    }

    private static void printFilesTree(File directory) {
        System.out.println("dir: " + directory.getName());
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printFilesTree(file);
                } else {
                    System.out.println("-> file: " + file.getName());
                }
            }
        }
    }
}
