package com.epam.auction.util;

import com.epam.auction.entity.Bid;
import com.google.gson.JsonObject;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ConverterTest {

    @Test
    public void test() {
        List<Bid> items = new ArrayList<>();
        items.add(new Bid(1, 1, 1, new BigDecimal(1)));
        items.add(new Bid(1, 1, 1, new BigDecimal(1)));

        Boolean has = new Boolean(true);

        String res = Converter.objectToJson(items) + Converter.objectToJson(has);

        JsonObject js = new JsonObject();

        js.addProperty("has", has);

        js.addProperty("res", res);

        System.out.println(js.toString());

    }

}
