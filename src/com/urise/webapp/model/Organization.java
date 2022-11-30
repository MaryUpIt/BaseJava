package com.urise.webapp.model;

import java.util.ArrayList;
import java.util.List;

public class Organization {
    private String title;
    private String website;
    private List<Period> positions;

    public Organization(String title, String website) {
        this.title = title;
        this.website = website;
        positions = new ArrayList<>();
    }

    public Organization(String title, String website, List<Period> positions) {
        this.title = title;
        this.website = website;
        this.positions = positions;
    }


    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public List<Period> getPositions() {
        return positions;
    }

    public void addPosition(Period period) {
        positions.add(period);
    }

    @Override
    public String toString() {
        StringBuilder organization = new StringBuilder("");
        organization.append(String.format("Corporation: %s\n", title));
        for (Period period : positions) {
            organization.append(period + "\n");
        }
        return organization.toString();
    }
}
