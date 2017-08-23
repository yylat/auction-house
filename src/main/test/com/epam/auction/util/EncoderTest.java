package com.epam.auction.util;

import org.junit.Test;

import java.util.Base64;

public class EncoderTest {

    @Test
    public void test(){
        String password = "Dartvader1";
        String co = Encoder.encode(password);
        System.out.println(co);
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] hash1 = decoder.decode("Vm14aFpERXlNMkYxWTNScGIyNWZkWE5sY2c9PQ==");
        String co1 = new String(hash1);

        byte[] hash2 = decoder.decode(hash1);
        String co2 = new String(hash2);
        System.out.println(co1 + " " + co2);
    }

}
