package com.urise.webapp.model;

public enum ContactType {
    PHONE("телефон"),
    SKYPE("skype"),
    EMAIL("email"),
    LINKEDIN ("linkedIn"),
    GITHUB("gitHub"),
    STACKOVERFLOW("stackoverflow"),
    HOMEPAGE("web-site");

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
