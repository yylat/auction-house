package com.epam.auction.util;

import java.util.Calendar;
import java.sql.Date;

public class DateFixer {

    public static Date fix(Date auctionDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(auctionDate);
        calendar.add(Calendar.DATE, 2);
        return new Date(calendar.getTimeInMillis());
    }

}