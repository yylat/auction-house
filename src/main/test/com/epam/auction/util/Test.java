package com.epam.auction.util;

import com.epam.auction.dao.filter.FilterCriteria;
import com.epam.auction.dao.filter.FilterQueryParameter;

import java.util.HashMap;
import java.util.Map;

public class Test {

    @org.junit.Test
    public void test() {
        Map<String, String[]> requestContent = new HashMap<>();
        requestContent.put("BLITZ_PRICE_FLOOR", new String[]{"12"});
        requestContent.put("BLITZ_PRICE_CEILING", new String[]{"13"});
        requestContent.put("ORDER_BY", new String[]{"actual_price"});
        requestContent.put("ORDER_TYPE", new String[]{"asc"});

        FilterCriteria filterCriteria = new FilterCriteria();

        for (FilterQueryParameter filterQueryParameter : FilterQueryParameter.values()) {
            String[] requestParameter = requestContent.get(filterQueryParameter.name());
            if (requestParameter != null) {
                filterCriteria.put(filterQueryParameter, requestParameter[0]);
            }
        }




    }

}
