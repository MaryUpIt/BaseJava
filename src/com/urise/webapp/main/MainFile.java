package com.urise.webapp.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String pathName = "..\\basejava\\src\\com\\urise\\webapp";
        pathName = "..\\basejava\\resumes";
        File fileDirectory = new File(pathName);
        System.out.println(fileDirectory.getCanonicalFile());
        System.out.println(fileDirectory.getName());
        System.out.println(fileDirectory.isDirectory());
//        printFilesTree(fileDirectory);
        Path pathDirectory = Paths.get(pathName);
        System.out.println(pathDirectory.getFileName());
        System.out.println(pathDirectory.toAbsolutePath().getRoot());
        System.out.println(Files.isDirectory(pathDirectory));




    }

    private static void printFilesTree(File directory) {
        System.out.println("Directory: " + directory.getName());
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
