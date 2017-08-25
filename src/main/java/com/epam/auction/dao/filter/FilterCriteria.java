package com.epam.auction.dao.filter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FilterCriteria {

    private final static String SEPARATOR = " ";
    private final static String AND = "AND";

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

    public String buildWhereClause() {
        StringBuilder stringBuilder = new StringBuilder();

        Iterator<FilterQueryParameter> parameterIterator = filterQueryParameters.iterator();
        while (parameterIterator.hasNext()) {
            stringBuilder.append(SEPARATOR).append(parameterIterator.next().getQueryPart());
            if (parameterIterator.hasNext()) {
                stringBuilder.append(SEPARATOR).append(AND);
            }
        }

        return stringBuilder.toString();
    }

    public List<Object> getValues() {
        return values;
    }

    public int getValuesSize() {
        return values.size();
    }
}