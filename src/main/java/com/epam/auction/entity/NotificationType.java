package com.epam.auction.entity;

public enum NotificationType {

    ITEM_CONFIRMED(1),
    ITEM_NOT_CONFIRMED(2),
    ITEM_SOLD(3),
    NO_BIDS_FOR_ITEM(4),
    SELLER_CANCELED_AUCTION(5),
    BID_WIN(6),
    BID_BEATEN(7);

    private int id;

    NotificationType(int id) {
        this.id = id;
    }



}