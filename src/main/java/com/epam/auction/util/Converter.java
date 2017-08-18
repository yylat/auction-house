package com.epam.auction.util;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class Converter {

    public static String objectToJson(Object object) {
        return new Gson().toJson(object);
    }

    public static String inputStreamToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }

        return Base64.getEncoder().encodeToString(result.toByteArray());
    }

}