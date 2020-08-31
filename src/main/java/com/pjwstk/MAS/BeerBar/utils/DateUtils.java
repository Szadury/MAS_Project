package com.pjwstk.MAS.BeerBar.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class DateUtils {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static int getYearFromDate(String date) {
        return Integer.parseInt(date.substring(0, 4));
    }

    public static int getMonthFromDate(String date) {
        return Integer.parseInt(date.substring(5, 7));
    }

    public static int getDayFromDate(String date) {
        return Integer.parseInt(date.substring(8, 10));
    }

    public static LocalDateTime createLocalDateTime(int year, int month, int day, int startTime) {
        LocalDate ld = LocalDate.of(year, month, day);
        LocalTime lt = LocalTime.of(startTime, 0);
        return LocalDateTime.of(ld, lt);
    }

    public static boolean isCorrectDateFormat(String dateValue) {
        if (dateValue != null) {
            try {
                Date date = dateFormat.parse(dateValue);
            } catch (ParseException pe) {
                return false;
            }
            return true;
        }
        return false;
    }
}
