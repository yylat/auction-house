package com.epam.auction.entity;

import java.util.Arrays;

public enum ItemStatus {

    CREATED(1),
    CONFIRMED(2),
    ACTIVE(3),
    SOLD(4),
    CANCELED(5),
    ENDED(6),
    NOT_CONFIRMED(7);

    private int id;

    ItemStatus(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static ItemStatus define(int id) {
        return Arrays.stream(ItemStatus.values())
                .filter(itemStatus -> id == itemStatus.id)
                .findFirst()
                .orElse(null);
    }

}