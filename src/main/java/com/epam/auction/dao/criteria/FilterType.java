package com.epam.auction.dao.criteria;

import com.epam.auction.dao.TableConstant;
import com.epam.auction.util.StringTransformer;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Stores columns, their types and method of comparing
 * for 'WHERE' SQL clause of `item` table. Provides service
 * to transform String filter values to their type.
 */
public enum FilterType {

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

    /**
     * Stores query representations for methods of comparing.
     */
    private class QueryOperator {
        private static final String LESS_OR_EQUAL = " <= ?";
        private static final String MORE_OR_EQUAL = " >= ?";
        private static final String EQUAL = " = ?";
        private static final String LIKE = " LIKE ?";
    }

    /**
     * Column query part.
     */
    private String queryPart;
    /**
     * Column type.
     */
    private Class parameterClass;

    /**
     * Constructs FilterType with column, method of comparing and column type.
     *
     * @param column         column
     * @param operator       method of comparing
     * @param parameterClass column type
     */
    FilterType(String column, String operator, Class parameterClass) {
        this.queryPart = column + operator;
        this.parameterClass = parameterClass;
    }

    /**
     * Transform String value to specific type from FilterType object.
     *
     * @param value String value of filter
     * @return value of filter with specific type from FilterType object
     */
    public Object transform(String value) {
        Object result;
        if (this.equals(SEARCH_NAME)) {
            result = "%" + value + "%";
        } else {
            result = StringTransformer.transform(this.parameterClass, value);
        }
        return result;
    }

    /**
     * Returns query part.
     *
     * @return query part
     */
    String getQueryPart() {
        return queryPart;
    }

    /**
     * Returns type of the column.
     *
     * @return type of the column
     */
    public Class getParameterClass() {
        return parameterClass;
    }

}