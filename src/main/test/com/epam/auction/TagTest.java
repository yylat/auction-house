package com.epam.auction;

import org.junit.Test;

public class TagTest {

    private static final String PAGINATION_COMMAND_PATTERN = "data-command=\"\"";
    private static final String FILTER_COMMAND_PATTERN = "name=\"command\" value=\"\"";

    @Test
    public void test() {
        String str =
                "<div id=\"pageBar\" class=\"w3-bar w3-small w3-margin-top\" data-command=\"\"\n" +
                " data-page=\"${requestScope.page}\" data-pages=\"${requestScope.pages}\">";

        StringBuilder bodyBuilder = new StringBuilder(str);

        String command = "load-active-items";
        bodyBuilder.insert(bodyBuilder.indexOf(PAGINATION_COMMAND_PATTERN) + PAGINATION_COMMAND_PATTERN.length() - 1, command);
        bodyBuilder.insert(bodyBuilder.indexOf(FILTER_COMMAND_PATTERN) + FILTER_COMMAND_PATTERN.length() - 1, command);

        System.out.println(str);
        System.out.println();
        System.out.println();
        System.out.println(bodyBuilder.toString());

        System.out.println(bodyBuilder.indexOf("lol"));

    }

}
