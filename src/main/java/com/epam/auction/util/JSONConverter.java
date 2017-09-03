package com.epam.auction.util;

import com.google.gson.Gson;

public class JSONConverter {

    public static String objectAsJson(Object object) {
        return new Gson().toJson(object);
    }

}