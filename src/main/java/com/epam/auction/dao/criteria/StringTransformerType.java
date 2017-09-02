package com.epam.auction.dao.criteria;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.function.Function;

class StringTransformerType {

    private static final Function<String, BigDecimal> toBigDecimal = BigDecimal::new;
    private static final Function<String, Date> toDate = Date::valueOf;
    private static final Function<String, Integer> toInt = Integer::valueOf;

    static Function<String, ?> get(Class clazz) {
        if (clazz.equals(BigDecimal.class)) {
            return toBigDecimal;
        } else if (clazz.equals(Date.class)) {
            return toDate;
        } else if (clazz.equals(Integer.class)) {
            return toInt;
        }
        return null;
    }

}