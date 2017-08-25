package com.epam.auction.receiver.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.function.Function;

public class StringTransformerType {

    private static final Function<String, BigDecimal> toBigDecimal = BigDecimal::new;
    private static final Function<String, Date> toDate = Date::valueOf;

    public static Function<String, ?> get(Class clazz) {
        if (clazz.equals(BigDecimal.class)) {
            return toBigDecimal;
        } else if (clazz.equals(Date.class)) {
            return toDate;
        }
        return null;
    }

}