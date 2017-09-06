package com.epam.auction.util;

import java.util.Calendar;
import java.sql.Date;

/**
 * Provides service for add days to date.
 */
public class DateFixer {

    /**
     * Adds days to current date.
     *
     * @param days days to add
     * @return date with added days
     */
    public static Date addDays(int days) {
        Calendar calendar = Calendar.getInstance();
        resetTime(calendar);
        calendar.add(Calendar.DATE, days);
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * Adds days to date.
     *
     * @param date date
     * @param days days to add
     * @return date with added days
     */
    public static Date addDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        resetTime(calendar);
        calendar.add(Calendar.DATE, days);
        return new Date(calendar.getTimeInMillis());
    }

    private static void resetTime(Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }

}