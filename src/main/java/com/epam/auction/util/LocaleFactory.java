package com.epam.auction.util;

import java.util.Arrays;
import java.util.Locale;

public enum LocaleFactory {

    EN("en", "US"),
    RU("ru", "RU");

    private Locale locale;

    LocaleFactory(String language, String country) {
        this.locale = new Locale(language, country);
    }

    public static Locale getLocale(String language) {
        LocaleFactory localeFactory = Arrays.stream(LocaleFactory.values())
                .filter(factory -> language.equals(factory.locale.getLanguage()))
                .findFirst()
                .orElse(LocaleFactory.EN);
        return localeFactory.locale;
    }

}