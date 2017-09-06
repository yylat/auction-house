package com.epam.auction.util;

import java.util.Arrays;
import java.util.Locale;

/**
 * Stores type of the locales.
 */
public enum LocaleType {

    /**
     * English locale attributes.
     */
    EN("en", "US"),
    /**
     * Russian locale attributes.
     */
    RU("ru", "RU");

    private Locale locale;

    public Locale getLocale() {
        return locale;
    }

    LocaleType(String language, String country) {
        this.locale = new Locale(language, country);
    }

    /**
     * Returns locale with language.
     *
     * @param language language of the locale
     * @return locale with language
     */
    public static Locale getLocale(String language) {
        LocaleType localeType = Arrays.stream(LocaleType.values())
                .filter(factory -> language.equals(factory.locale.getLanguage()))
                .findFirst()
                .orElse(LocaleType.EN);
        return localeType.locale;
    }

}