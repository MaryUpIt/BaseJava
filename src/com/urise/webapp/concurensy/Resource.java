package com.urise.webapp.concurensy;

public class Resource {
    private int nonStatic;
    private static int isStatic;
    private String string;

    public Resource(int nonStatic) {
        this.nonStatic = nonStatic;
    }

    public Resource(String string) {
        this.string = string;
    }

    public static synchronized void changeStatic() {
        isStatic++;
    }

    public static synchronized int getIsStatic() {
        return isStatic;
    }

    public void changeNonStatic() {
        nonStatic++;
    }

    public int getNonStatic() {
        return nonStatic;
    }

    @Override
    public String toString() {
        return string;
    }
}
