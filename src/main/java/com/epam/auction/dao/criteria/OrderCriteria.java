package com.epam.auction.dao.criteria;

import com.epam.auction.dao.TableConstant;

import java.util.HashSet;
import java.util.Set;

/**
 * Provides service to construct 'ORDER BY' SQL clause for `item` table.
 */
public class OrderCriteria {

    /**
     * Set of columns for sorting.
     */
    private static final Set<String> orderedColumns = new HashSet<>();

    static {
        orderedColumns.add(TableConstant.ITEM_COLUMN_ID);
        orderedColumns.add(TableConstant.ITEM_COLUMN_BLITZ_PRICE);
        orderedColumns.add(TableConstant.ITEM_COLUMN_ACTUAL_PRICE);
        orderedColumns.add(TableConstant.ITEM_COLUMN_START_DATE);
        orderedColumns.add(TableConstant.ITEM_COLUMN_CLOSE_DATE);
    }

    /**
     * Ascending sort type.
     */
    private static final String TYPE_ASC = "ASC";
    /**
     * Descending sort type.
     */
    private static final String TYPE_DESC = "DESC";

    /**
     * 'ORDER BY' clause.
     */
    private String orderByClause = " ORDER BY `item`.";

    /**
     * Constructs OrderCriteria.
     */
    public OrderCriteria() {
        orderByClause += TableConstant.ITEM_COLUMN_ID;
        orderByClause += " " + TYPE_DESC;
    }

    /**
     * Constructs OrderCriteria with column to sort.
     *
     * @param column column to sort
     */
    public OrderCriteria(String column) {
        column = column.toLowerCase().replaceAll("-", "_");
        if (orderedColumns.contains(column)) {
            orderByClause += column;
        } else {
            orderByClause += TableConstant.ITEM_COLUMN_ID;
        }
    }

    /**
     * Constructs OrderCriteria with column to sort and sort type.
     *
     * @param column column to sort
     * @param type   sort type
     */
    public OrderCriteria(String column, String type) {
        this(column);
        if (TYPE_ASC.equalsIgnoreCase(type.toLowerCase())) {
            orderByClause += " " + TYPE_ASC;
        } else {
            orderByClause += " " + TYPE_DESC;
        }
    }

    /**
     * Returns 'ORDER BY' SQL clause for `item` table.
     *
     * @return 'ORDER BY' SQL clause for `item` table
     */
    public String getOrderByClause() {
        return orderByClause;
    }

}