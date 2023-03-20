package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("MM:yyyy");
    public final static LocalDate NOW = LocalDate.now();

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate parse(String value) {
        YearMonth yearMonth = YearMonth.parse(value, DATE_TIME_FORMATTER);
        return HtmlUtil.isEmpty(value) ? NOW : LocalDate.of(yearMonth.getYear(), yearMonth.getMonth(),1);
    }

    public static String format(LocalDate date) {
        return date == null ? "" : date.format(DATE_TIME_FORMATTER);
    }
}
