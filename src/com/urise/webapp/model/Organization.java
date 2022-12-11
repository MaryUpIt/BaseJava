package com.urise.webapp.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Organization implements Serializable {
    private final String title;
    private final String website;

    private final List<Period> periods;

    public Organization(String title, String website, List<Period> periods) {
        this.title = title;
        this.website = website;
        if (periods == null) {
            this.periods = new ArrayList<>();
        } else {
            this.periods = periods;
        }
    }

    public Organization(String title, String website, Period...periods) {
        this(title, website, Arrays.asList(periods));

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
        return title.equals(that.title) && website.equals(that.website)
                && periods.equals(that.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, website, periods);
    }

    public static class Period implements Serializable{
        public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM:yyyy");
        private String position;
        private String responsibilities;
        private LocalDate dateFrom;
        private LocalDate dateTo;

        public Period(String position, String responsibilities, LocalDate dateFrom, LocalDate dateTo) {
            this.position = position;
            this.responsibilities = responsibilities;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }


        @Override
        public String toString() {
            StringBuilder descriptions = new StringBuilder("");
            String period = dateFormat.format(dateFrom) + " - " + dateFormat.format(dateTo);
            descriptions.append(String.format("%s | position:  %s \n %s", period, position, responsibilities));
            return descriptions.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return Objects.equals(position, period.position) && Objects.equals(responsibilities, period.responsibilities)
                    && Objects.equals(dateFrom, period.dateFrom) && Objects.equals(dateTo, period.dateTo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, responsibilities, dateFrom, dateTo);
        }
    }
}
