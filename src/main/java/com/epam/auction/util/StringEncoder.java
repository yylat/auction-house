package com.epam.auction.util;

import java.math.BigInteger;
import java.util.Base64;

public class StringEncoder {

    private static final String SALT = "auction_user";

    public static String encode(String originalString) {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] bytesEncoded = encoder.encode((originalString + SALT).getBytes());
        BigInteger bigInteger = new BigInteger(1, bytesEncoded);
        return bigInteger.toString(16);
    }

}