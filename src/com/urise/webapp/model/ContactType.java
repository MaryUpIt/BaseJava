package com.urise.webapp.model;

public enum ContactType {
    PHONE("PHONE"),
    SKYPE("SKYPE"),
    EMAIL("EMAIL"),
    LINKEDIN ("LINKEDIN"),
    GITHUB("GITHUB"),
    STACKOVERFLOW("STACKOVERFLOW"),
    HOMEPAGE("HOMEPAGE");

    private String title;

    ContactType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
    public String toHtml(String value){
        return title +" : " + value;
    }
}
