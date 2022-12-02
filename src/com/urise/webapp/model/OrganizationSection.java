package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {

    private final List<Organization> content;

    public OrganizationSection(List<Organization> content) {
        this.content = content;
    }

    public OrganizationSection() {
        content = new ArrayList<>();
    }


    public void addOrganization(Organization organization) {
        content.add(organization);
    }

    @Override
    public String toString() {
        StringBuilder organizations = new StringBuilder("");
        for (Organization organization : content) {
            organizations.append(organization + "\n");
        }

        return organizations.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
