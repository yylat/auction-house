package com.epam.auction.dao.criteria;

import com.epam.auction.exception.WrongFilterParameterException;

import java.util.*;

/**
 * Provides service to construct 'WHERE' SQL clause for `item` table.
 */
public class FilterCriteria {

    private static final String WHERE = "WHERE";
    private static final String SEPARATOR = " ";
    private static final String AND = "AND";

    /**
     * List of values to filter.
     */
    private List<Object> filterValues;
    /**
     * 'WHERE' clause.
     */
    private StringBuilder whereClause = new StringBuilder(SEPARATOR);

    /**
     * Constructs FilterCriteria.
     */
    public FilterCriteria() {
        filterValues = new ArrayList<>();
    }

    /**
     * Transform value to filter from String and puts it
     * into filter values list. Creates part of the query
     * according to {@link FilterType}.
     *
     * @param filterType
     * @param value                value to filter
     */
    public void put(FilterType filterType, String value) {
        this.filterValues.add(filterType.transform(value));
        buildQueryPart(filterType);
    }

    /**
     * Puts value to filter into filter values list, if
     * it's class is the according class from {@link FilterType}.
     * Creates part of the query
     * according to {@link FilterType}.
     *
     * @param filterType
     * @param value                value to filter
     * @param <T>                  type of the value to filter
     * @throws WrongFilterParameterException if class of the value to filter is the
     *                                       according class from {@link FilterType}
     */
    public <T> void put(FilterType filterType, T value) throws WrongFilterParameterException {
        if (filterType.getParameterClass().equals(value.getClass())) {
            this.filterValues.add(value);
            buildQueryPart(filterType);
        } else {
            throw new WrongFilterParameterException();
        }
    }

    /**
     * Returns full 'WHERE' SQL clause for `item` table.
     *
     * @return full 'WHERE' SQL clause for `item` table
     */
    public String getWhereClause() {
        return WHERE + whereClause.toString();
    }

    /**
     * Returns part of the 'WHERE' SQL clause for `item` table without 'WHERE'.
     *
     * @return part of the 'WHERE' SQL clause for `item` table
     */
    public String getWhereClausePart() {
        if (!isInitialParameter()) {
            return SEPARATOR + AND + whereClause.toString();
        }
        return SEPARATOR;
    }

    /**
     * Returns all values to filter.
     *
     * @return all values to filter
     */
    public List<Object> getFilterValues() {
        return filterValues;
    }

    /**
     * Returns <code>true</code> if whereClause contains only
     * space in it, <code>false</code> otherwise.
     *
     * @return <code>true</code> if whereClause contains only
     * space in it, <code>false</code> otherwise
     */
    private boolean isInitialParameter() {
        return whereClause.length() == SEPARATOR.length();
    }

    /**
     * Builds part of the query
     * according to {@link FilterType}.
     *
     * @param filterType
     */
    private void buildQueryPart(FilterType filterType) {
        if (isInitialParameter()) {
            whereClause = whereClause.append(filterType.getQueryPart()).append(SEPARATOR);
        } else {
            whereClause.append(AND).append(SEPARATOR)
                    .append(filterType.getQueryPart()).append(SEPARATOR);
        }
    }

}