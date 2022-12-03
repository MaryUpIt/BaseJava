package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Period {
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
