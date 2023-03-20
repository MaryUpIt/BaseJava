package com.urise.webapp.model;

public enum SectionType {
    OBJECTIVE("OBJECTIVE"),
    PERSONAL("PERSONAL"),
    ACHIEVEMENTS("ACHIEVEMENTS"),
    QUALIFICATIONS("QUALIFICATIONS"),
    EDUCATION("EDUCATION"),
    EXPERIENCE("EXPERIENCE");

    private String title;

    SectionType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String toHtml(String value) {
        return value == null ? "" : title + " : " + value;
    }
}
