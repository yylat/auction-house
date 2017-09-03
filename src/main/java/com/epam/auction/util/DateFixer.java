package com.epam.auction.util;

import java.util.Calendar;
import java.sql.Date;

public class DateFixer {

    public static Date addDays(int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, days);
        return new Date(calendar.getTimeInMillis());
    }

    public static Date addDays(Date auctionDate, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(auctionDate);
        calendar.add(Calendar.DATE, days);
        return new Date(calendar.getTimeInMillis());
    }

}