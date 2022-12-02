package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Organization {
    private String title;
    private String website;
    private List<Period> periods;

    public Organization(String title, String website) {
        this.title = title;
        this.website = website;
        periods = new ArrayList<>();
    }

    public Organization(String title, String website, List<Period> periods) {
        this.title = title;
        this.website = website;
        if (periods == null) {
            this.periods = new ArrayList<>();
        } else {
            this.periods = periods;
        }
    }


    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPeriods() {
        return periods;
    }

    public void addPosition(Period period) {
        periods.add(period);
    }

    @Override
    public String toString() {
        StringBuilder organization = new StringBuilder("");
        organization.append(String.format("Corporation: %s\n", title));
        for (Period period : periods) {
            organization.append(period + "\n");
        }
        return organization.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organization that = (Organization) o;
        return Objects.equals(title, that.title) && Objects.equals(website, that.website)
                && Objects.equals(periods, that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, website, periods);
    }
}
