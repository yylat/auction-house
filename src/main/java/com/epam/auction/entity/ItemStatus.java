package com.epam.auction.entity;

import java.util.Arrays;

public enum ItemStatus {

    CREATED,
    CONFIRMED,
    ACTIVE,
    SOLD,
    CANCELED,
    ENDED,
    NOT_CONFIRMED;

    public static ItemStatus define(int id) {
        return Arrays.stream(ItemStatus.values())
                .filter(itemStatus -> id == itemStatus.ordinal())
                .findFirst()
                .orElse(null);
    }

}
