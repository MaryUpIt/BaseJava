package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class OrganizationSection extends AbstractSection {
    private List<Organization> organizations;

    public OrganizationSection() {
    }

    public OrganizationSection(List<Organization> organizations) {
        Objects.requireNonNull(organizations, "organization must not be null");
        this.organizations = organizations;
    }

    public OrganizationSection(Organization... organizations) {
        this(Arrays.asList(organizations));
    }

    public void addOrganization(Organization organization) {
        if (organizations == null) {
            organizations = new ArrayList<>();
        }
        organizations.add(organization);
    }

    public List<Organization> getContent() {
        return organizations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection section = (OrganizationSection) o;
        return organizations.equals(section.organizations);
    }

    @Override
    public int hashCode() {
        return organizations.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder organizations = new StringBuilder("");
        for (Organization organization : this.organizations) {
            organizations.append(organization);
        }

        return organizations.toString();
    }
}
