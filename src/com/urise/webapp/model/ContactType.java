package com.urise.webapp.model;

public enum ContactType {
    PHONE("Phone number"),
    SKYPE("Skype"),
    EMAIL("Mail"),
    LINKEDIN ("LinkedIn"),
    GITHUB("GitHub"),
    STACKOVERFLOW("StackOverflow"),
    HOMEPAGE("Homepage");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
