package com.urise.webapp.model;

import java.util.Map;

public class OrganizationSections extends AbstractSection {
    private Map<Period, Organization> sections;

    public OrganizationSections(Map<Period, Organization> sections) {
        this.sections = sections;
    }

    @Override
    public String toString() {
        StringBuilder organization = new StringBuilder("");
        for (Map.Entry<Period, Organization> entry : sections.entrySet()) {
            organization.append(entry.getKey() + " : " + entry.getValue()+"\n");
        }
        return organization.toString();
    }
}
