package com.epam.auction.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class StringTransformerTest {

    private Class clazz;
    private String value;
    private Object result;

    public StringTransformerTest(Class clazz, String value, Object result) {
        this.clazz = clazz;
        this.value = value;
        this.result = result;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {Integer.class, "12", 12},
                {BigDecimal.class, "300.34", new BigDecimal("300.34")},
                {Long.class, "23012435", 23012435L},
                {Date.class, "2017-09-06", Date.valueOf("2017-09-06")}
        });
    }

    @Test
    public void transformTest() {
        Object result = StringTransformer.transform(clazz, value);
        assertThat(result, is(both
                (is(this.result)).and(instanceOf(this.clazz))));
    }

}