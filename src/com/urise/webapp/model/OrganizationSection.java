package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrganizationSection extends AbstractSection {

    private final List<Organization> organizations;

    public OrganizationSection(List<Organization> organizations) {
        if (organizations == null) {
            this.organizations = new ArrayList<>();
        } else {
            this.organizations = organizations;
        }
    }

    public OrganizationSection() {
        organizations = new ArrayList<>();
    }


    public void addOrganization(Organization organization) {
        organizations.add(organization);
    }

    @Override
    public String toString() {
        StringBuilder organizations = new StringBuilder("");
        for (Organization organization : this.organizations) {
            organizations.append(organization + "\n");
        }

        return organizations.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationSection that = (OrganizationSection) o;
        return organizations.equals(that.organizations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizations);
    }
}
