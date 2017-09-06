package com.epam.auction.util;

import com.google.gson.Gson;

/**
 * Converts object to JSON string.
 */
public class JSONConverter {

    /**
     * Converts object to JSON string.
     *
     * @param object object
     * @return string with JSON representation of the object
     */
    public static String objectAsJson(Object object) {
        return new Gson().toJson(object);
    }

}