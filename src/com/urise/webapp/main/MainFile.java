package com.urise.webapp.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainFile {
    public static void main(String[] args) throws IOException {
        String pathName = "..\\basejava\\src\\com\\urise";
        //  pathName = "..\\basejava\\resumes";
        File fileDirectory = new File(pathName);
        System.out.println(fileDirectory.getCanonicalFile());
        System.out.println(fileDirectory.getName());
        System.out.println(fileDirectory.isDirectory());
        printFilesTree(fileDirectory, "");
        Path pathDirectory = Paths.get(pathName);
        System.out.println(pathDirectory.getFileName());
        System.out.println(pathDirectory.toAbsolutePath().getRoot());
        System.out.println(Files.isDirectory(pathDirectory));
        printFilesTree(pathDirectory);


    }

    private static void printFilesTree(File directory, String offset) {
        System.out.println(offset +"Directory: " + directory.getName());
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    printFilesTree(file, offset + "  ");
                } else {
                    System.out.println(offset + "  -> file: " + file.getName());
                }
            }
        }
    }

    private static void printFilesTree(Path directory) {
        if (Files.isDirectory(directory)) {
            System.out.println("Directory: " + directory.getFileName());
            try {
                Files.list(directory).forEach(path -> {
                    if (Files.isDirectory(path)) {
                        printFilesTree(path);
                    } else {
                        System.out.println("-> file: " + path.getFileName());
                    }
                });
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }

        }
    }
}
