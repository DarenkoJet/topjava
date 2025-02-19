package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    
    public static boolean isBetweenHalfOpen(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return !lt.isBefore(startTime) && lt.isBefore(endTime);
    }
    
    public static boolean isBetweenHalfOpen(Temporal t, Temporal start, Temporal end) {
        if (t instanceof LocalTime) {
            LocalTime lt = (LocalTime) t;
            return !lt.isBefore((LocalTime) start) && lt.isBefore((LocalTime) end);
        } else {
            LocalDate ld = (LocalDate) t;
            return !ld.isBefore((LocalDate) start) && ld.isBefore((LocalDate) end);
        }
    }
    
    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

