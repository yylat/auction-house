package com.epam.auction.util;

import java.util.Arrays;
import java.util.Locale;

public enum LocaleType {

    EN("en", "US"),
    RU("ru", "RU");

    private Locale locale;

    LocaleType(String language, String country) {
        this.locale = new Locale(language, country);
    }

    public static Locale getLocale(String language) {
        LocaleType localeType = Arrays.stream(LocaleType.values())
                .filter(factory -> language.equals(factory.locale.getLanguage()))
                .findFirst()
                .orElse(LocaleType.EN);
        return localeType.locale;
    }

}