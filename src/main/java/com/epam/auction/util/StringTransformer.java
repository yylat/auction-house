package com.epam.auction.util;

import java.math.BigDecimal;
import java.sql.Date;

public class StringTransformer {

    public static Object transform(Class clazz, String value) {
        if (BigDecimal.class.equals(clazz)) {
            return new BigDecimal(value);
        } else if (Date.class.equals(clazz)) {
            return Date.valueOf(value);
        } else if (Long.class.equals(clazz)) {
            return Long.valueOf(value);
        } else if (Integer.class.equals(clazz)) {
            return Integer.valueOf(value);
        } else {
            return value;
        }
    }

}