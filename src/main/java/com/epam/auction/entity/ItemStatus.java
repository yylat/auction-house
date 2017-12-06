package com.epam.auction.entity;

import java.util.Arrays;

/**
 * Represents `item_status` table from database.
 */
public enum ItemStatus {

    /**
     * Item just created.
     */
    CREATED,
    /**
     * Item approved for auction.
     */
    CONFIRMED,
    /**
     * Item put up for auction.
     * Users can make bids.
     */
    ACTIVE,
    /**
     * Item sold.
     */
    SOLD,
    /**
     * Seller canceled the auction.
     */
    CANCELED,
    /**
     * Auction time is over.
     * No bids was made.
     */
    ENDED,
    /**
     * Item didn't approve.
     */
    NOT_CONFIRMED,

    DELIVERED,

    VIOLATION;

    /**
     * Returns status of the item with id (ordinal number in enum).
     *
     * @param id id of the item
     * @return status
     */
    public static ItemStatus define(int id) {
        return Arrays.stream(ItemStatus.values())
                .filter(itemStatus -> id == itemStatus.ordinal())
                .findFirst()
                .orElse(null);
    }

}
