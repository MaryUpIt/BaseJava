package com.urise.webapp.model;

import com.urise.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organization implements Serializable {
    final public static Organization EMPTY = new Organization("", "", Period.EMPTY);
    public static final Comparator<Period> PERIOD_COMPARATOR = Comparator.comparing(Period::getDateFrom).thenComparing(Period::getDateTo);

    private String title;
    private String website;
    private List<Period> periods;

    public Organization() {
    }

    public Organization(String title, String website) {
        Objects.requireNonNull(title, "title must not be null");
        this.title = title;
        this.website = website == null ? "" : website;
    }

    public Organization(String title, String website, List<Period> periods) {
        this(title, website);
        periods.sort(PERIOD_COMPARATOR);
        this.periods = periods;
    }

    public Organization(String title, String website, Period... periods) {
        this(title, website, Arrays.asList(periods));

    }

    public void addPeriod(Period period) {
        if (periods == null) {
            periods = new ArrayList<>();
        }
        periods.add(period);
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
    public static class Period implements Comparable<Period>, Serializable {
        public static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM:yyyy");
        public static final Period EMPTY = new Period();
        private String position;

        public String getPosition() {
            return position;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getDateFrom() {
            return dateFrom;
        }

        public LocalDate getDateTo() {
            return dateTo;
        }

        private String description;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateFrom;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate dateTo;

        public Period() {
        }

        public Period(String position, String description, LocalDate dateFrom, LocalDate dateTo) {
            Objects.requireNonNull(position, "position must not be null");
            Objects.requireNonNull(dateFrom, "dateFrom must not be null");
            Objects.requireNonNull(dateTo, "dateTo must not be null");
            this.position = position;
            this.description = description == null ? "" : description;
            this.dateFrom = dateFrom;
            this.dateTo = dateTo;
        }


        @Override
        public String toString() {
            StringBuilder descriptions = new StringBuilder("");
            String period = dateFormat.format(dateFrom) + " - " + dateFormat.format(dateTo);
            descriptions.append(String.format("%s | position:  %s \n %s", period, position, description));
            return descriptions.toString();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Period period = (Period) o;
            return position.equals(period.position)
                    && description.equals(period.description)
                    && dateFrom.equals(period.dateFrom)
                    && dateTo.equals(period.dateTo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(position, description, dateFrom, dateTo);
        }

        @Override
        public int compareTo(Period period) {
            return dateFrom.compareTo(period.dateFrom);
        }
    }
}
