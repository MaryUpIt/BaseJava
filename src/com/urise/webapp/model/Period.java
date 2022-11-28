package com.urise.webapp.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Period {
    private LocalDate dateFrom;
    private LocalDate dateTo;

    public Period(LocalDate dateFrom, LocalDate dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    @Override
    public String toString() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM.YYYY");
        return dateFormat.format(dateFrom) + " - " + dateFormat.format(dateTo);
    }
}
