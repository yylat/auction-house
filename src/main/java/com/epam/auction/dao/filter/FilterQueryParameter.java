package com.epam.auction.dao.filter;

import com.epam.auction.dao.TableConstant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Function;

public enum FilterQueryParameter {

    STATUS(TableConstant.ITEM_COLUMN_STATUS_ID, QueryOperator.equal, Integer.class),
    BLITZ_PRICE_FLOOR(TableConstant.ITEM_COLUMN_BLITZ_PRICE, QueryOperator.moreOrEqual, BigDecimal.class),
    BLITZ_PRICE_CEILING(TableConstant.ITEM_COLUMN_BLITZ_PRICE, QueryOperator.lessOrEqual, BigDecimal.class),
    ACTUAL_PRICE_FLOOR(TableConstant.ITEM_COLUMN_ACTUAL_PRICE, QueryOperator.moreOrEqual, BigDecimal.class),
    ACTUAL_PRICE_CEILING(TableConstant.ITEM_COLUMN_ACTUAL_PRICE, QueryOperator.lessOrEqual, BigDecimal.class),
    START_DATE_FLOOR(TableConstant.ITEM_COLUMN_START_DATE, QueryOperator.moreOrEqual, Date.class),
    START_DATE_CEILING(TableConstant.ITEM_COLUMN_START_DATE, QueryOperator.lessOrEqual, Date.class),
    CLOSE_DATE_FLOOR(TableConstant.ITEM_COLUMN_CLOSE_DATE, QueryOperator.moreOrEqual, Date.class),
    CLOSE_DATE_CEILING(TableConstant.ITEM_COLUMN_CLOSE_DATE, QueryOperator.lessOrEqual, Date.class),
    CATEGORY_ID(TableConstant.ITEM_COLUMN_CATEGORY_ID, QueryOperator.equal, Integer.class),
    SELLER_ID(TableConstant.ITEM_COLUMN_SELLER_ID, QueryOperator.equal, Integer.class);

    private static class QueryOperator {
        private final static String lessOrEqual = " <= ?";
        private final static String moreOrEqual = " >= ?";
        private final static String equal = " = ?";
    }

    private String queryPart;
    private Class parameterClass;

    FilterQueryParameter(String column, String operator, Class parameterClass) {
        this.queryPart = column + operator;
        this.parameterClass = parameterClass;
    }

    public Object transform(String value) {
        Function<String, ?> function = StringTransformerType.get(this.parameterClass);
        if (function != null) {
            return function.apply(value);
        } else {
            return value;
        }
    }

    String getQueryPart() {
        return queryPart;
    }

    public Class getParameterClass() {
        return parameterClass;
    }
}