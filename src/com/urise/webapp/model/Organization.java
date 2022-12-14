package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    private String title;
    private String website;
    private List<Period> periods = new ArrayList<>();

    public Organization() {
    }

    public Organization(String title, String website) {
        Objects.requireNonNull(title, "title must not be null");
        Objects.requireNonNull(website, "website must not be null");
        this.title = title;
        this.website = website;
    }

    public Organization(String title, String website, List<Period> periods) {
        this(title, website);
        this.periods = periods;
    }

    public Organization(String title, String website, Period... periods) {
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

//    public void addPosition(Period period) {
//        if (periods == null) {
//            periods = new ArrayList<>();
//        }
//        periods.add(period);
//    }

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
        Organization organization = (Organization) o;
        return title.equals(organization.title)
                && website.equals(organization.website)
                && periods.equals(organization.periods);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, website, periods);
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Period implements Serializable {
        public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM:yyyy");
        private String position;

        public String getPosition() {
            return position;
        }

        public String getResponsibilities() {
            return responsibilities;
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }

        private String responsibilities;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateTo;

        public Period() {
        }

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
            return position.equals(period.position)
                    && responsibilities.equals(period.responsibilities)
                    && dateFrom.equals(period.dateFrom)
                    && dateTo.equals(period.dateTo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, responsibilities, dateFrom, dateTo);
        }
    }
}
