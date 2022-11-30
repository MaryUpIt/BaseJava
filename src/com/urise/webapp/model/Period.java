package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
        descriptions.append(String.format("%s | position:  %s \n%s", period, position, responsibilities));
        return descriptions.toString();
    }
}
