package com.epam.auction.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

@RunWith(Parameterized.class)
public class DateFixerTest {

    private Date date;
    private int days;
    private Date result;

    public DateFixerTest(Date date, int days, Date result) {
        this.date = date;
        this.days = days;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Date.valueOf("2017-05-26"), 2, Date.valueOf("2017-05-28")},
                {Date.valueOf("2017-08-30"), 4, Date.valueOf("2017-09-03")}
        });
    }

    @Test
    public void testAddDays() {
        Date result = DateFixer.addDays(date, days);
        assertThat(result, is(this.result));
    }

}