package com.urise.webapp.model;

public class Organization {
    private String organization;
    private String position;

    public Organization(String organization, String position) {
        this.organization = organization;
        this.position = position;
    }

    @Override
    public String toString() {
        return organization + " : " + position;
    }
}
