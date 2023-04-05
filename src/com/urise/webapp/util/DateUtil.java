package com.urise.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    public final static LocalDate NOW = LocalDate.now();

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate parse(String value) {
//        YearMonth yearMonth = YearMonth.parse(value, DATE_TIME_FORMATTER);
        return HtmlUtil.isEmpty(value) ? NOW : LocalDate.from(DateTimeFormatter.ofPattern("yyyy-MM-dd").parse(value));
    }



    public static String format(LocalDate date) {
        return date == null ? "" : date.format(DATE_TIME_FORMATTER);
    }
}
