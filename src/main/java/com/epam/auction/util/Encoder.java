package com.epam.auction.util;

import java.util.Base64;

public class Encoder {

    private final static String SALT = "auction_user";

    public static String encode(String originalString){
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] hash = encoder.encode((originalString + SALT).getBytes());
        return new String(encoder.encode(hash));
    }

}