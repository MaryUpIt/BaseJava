package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class OrganizationSections extends AbstractSection {

    private List<Organization> sections;

    public OrganizationSections(List<Organization> sections) {
        this.sections = sections;
    }

    public OrganizationSections() {
        sections = new ArrayList<>();
    }


    public void addOrganization(Organization organization) {
        sections.add(organization);
    }

    @Override
    public String toString() {
        StringBuilder organizations = new StringBuilder("");
        for (Organization organization : sections) {
            organizations.append(organization + "\n");
        }

        return organizations.toString();
    }
}
