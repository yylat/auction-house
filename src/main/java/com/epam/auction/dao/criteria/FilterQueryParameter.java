package com.epam.auction.dao.criteria;

import com.epam.auction.dao.TableConstant;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Function;

public enum FilterQueryParameter {

    STATUS(TableConstant.ITEM_COLUMN_STATUS_ID, QueryOperator.EQUAL, Integer.class),
    BLITZ_PRICE_FLOOR(TableConstant.ITEM_COLUMN_BLITZ_PRICE, QueryOperator.MORE_OR_EQUAL, BigDecimal.class),
    BLITZ_PRICE_CEILING(TableConstant.ITEM_COLUMN_BLITZ_PRICE, QueryOperator.LESS_OR_EQUAL, BigDecimal.class),
    ACTUAL_PRICE_FLOOR(TableConstant.ITEM_COLUMN_ACTUAL_PRICE, QueryOperator.MORE_OR_EQUAL, BigDecimal.class),
    ACTUAL_PRICE_CEILING(TableConstant.ITEM_COLUMN_ACTUAL_PRICE, QueryOperator.LESS_OR_EQUAL, BigDecimal.class),
    START_DATE_FLOOR(TableConstant.ITEM_COLUMN_START_DATE, QueryOperator.MORE_OR_EQUAL, Date.class),
    START_DATE_CEILING(TableConstant.ITEM_COLUMN_START_DATE, QueryOperator.LESS_OR_EQUAL, Date.class),
    CLOSE_DATE_FLOOR(TableConstant.ITEM_COLUMN_CLOSE_DATE, QueryOperator.MORE_OR_EQUAL, Date.class),
    CLOSE_DATE_CEILING(TableConstant.ITEM_COLUMN_CLOSE_DATE, QueryOperator.LESS_OR_EQUAL, Date.class),
    CATEGORY_ID(TableConstant.ITEM_COLUMN_CATEGORY_ID, QueryOperator.EQUAL, Long.class),
    SELLER_ID(TableConstant.ITEM_COLUMN_SELLER_ID, QueryOperator.EQUAL, Long.class),
    SEARCH_NAME(TableConstant.ITEM_COLUMN_NAME, QueryOperator.LIKE, String.class);

    private static class QueryOperator {
        private static final String LESS_OR_EQUAL = " <= ?";
        private static final String MORE_OR_EQUAL = " >= ?";
        private static final String EQUAL = " = ?";
        private static final String LIKE = " LIKE ?";
    }

    private String queryPart;
    private Class parameterClass;

    FilterQueryParameter(String column, String operator, Class parameterClass) {
        this.queryPart = column + operator;
        this.parameterClass = parameterClass;
    }

    public Object transform(String value) {
        Object result;
        if (this.equals(SEARCH_NAME)) {
            result = "%" + value + "%";
        } else {
            Function<String, ?> function = StringTransformerType.get(this.parameterClass);
            if (function != null) {
                result = function.apply(value);
            } else {
                result = value;
            }
        }
        return result;
    }

    String getQueryPart() {
        return queryPart;
    }

    public Class getParameterClass() {
        return parameterClass;
    }

}