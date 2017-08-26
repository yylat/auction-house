package com.epam.auction.dao.filter;

import com.epam.auction.dao.TableConstant;

import java.util.HashSet;
import java.util.Set;

public class OrderCriteria {

    private static final Set<String> orderedColumns = new HashSet<>();

    static {
        orderedColumns.add(TableConstant.ITEM_COLUMN_ID);
        orderedColumns.add(TableConstant.ITEM_COLUMN_BLITZ_PRICE);
        orderedColumns.add(TableConstant.ITEM_COLUMN_ACTUAL_PRICE);
        orderedColumns.add(TableConstant.ITEM_COLUMN_START_DATE);
        orderedColumns.add(TableConstant.ITEM_COLUMN_CLOSE_DATE);
    }

    private static final String TYPE_ASC = "ASC";
    private static final String TYPE_DESC = "DESC";

    private String queryPart = " ORDER BY ";

    public OrderCriteria() {
        queryPart += TableConstant.ITEM_COLUMN_ID;
        queryPart += " " + TYPE_DESC;
    }

    public OrderCriteria(String column) {
        column = column.toLowerCase().replaceAll("-", "_");
        if (orderedColumns.contains(column)) {
            queryPart += column;
        } else {
            queryPart += TableConstant.ITEM_COLUMN_ID;
        }
    }

    public OrderCriteria(String column, String type) {
        this(column);
        if (TYPE_ASC.equalsIgnoreCase(type.toLowerCase())) {
            queryPart += " " + TYPE_ASC;
        } else {
            queryPart += " " + TYPE_DESC;
        }
    }

    public String getQueryPart() {
        return queryPart;
    }

}