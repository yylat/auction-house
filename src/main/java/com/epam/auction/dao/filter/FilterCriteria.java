package com.epam.auction.dao.filter;

import com.epam.auction.exception.WrongFilterParameterException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterCriteria {

    private static final String WHERE = "WHERE";
    private static final String SEPARATOR = " ";
    private static final String AND = "AND";

    private List<FilterQueryParameter> filterQueryParameters;
    private List<Object> values;

    public FilterCriteria() {
        filterQueryParameters = new ArrayList<>();
        values = new ArrayList<>();
    }

    public void put(FilterQueryParameter filterQueryParameter, String value) {
        this.filterQueryParameters.add(filterQueryParameter);
        this.values.add(filterQueryParameter.transform(value));
    }

    public <T> void put(FilterQueryParameter filterQueryParameter, T value) throws WrongFilterParameterException {
        this.filterQueryParameters.add(filterQueryParameter);
        if (filterQueryParameter.getParameterClass().equals(value.getClass())) {
            this.values.add(value);
        } else {
            throw new WrongFilterParameterException();
        }
    }

    public String buildWhereClause() {
        StringBuilder whereClause = new StringBuilder(WHERE).append(SEPARATOR);

        Iterator<FilterQueryParameter> iterator = filterQueryParameters.iterator();
        while (iterator.hasNext()) {
            whereClause.append(iterator.next().getQueryPart()).append(SEPARATOR);
            if (iterator.hasNext()) {
                whereClause.append(AND).append(SEPARATOR);
            }
        }

        return whereClause.toString();
    }

    public String buildWhereClausePart() {
        StringBuilder whereClause = new StringBuilder(SEPARATOR);

        for (FilterQueryParameter filterQueryParameter : filterQueryParameters) {
            whereClause.append(AND).append(SEPARATOR).append(filterQueryParameter.getQueryPart()).append(SEPARATOR);
        }

        return whereClause.toString();
    }

    public List<Object> getValues() {
        return values;
    }

}