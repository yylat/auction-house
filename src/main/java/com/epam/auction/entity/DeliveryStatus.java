package com.epam.auction.entity;

import java.util.Arrays;

public enum DeliveryStatus {

    /**
     * Delivery status not specified.
     */
    NO_DELIVERY,

    /**
     * Seller confirmed delivery.
     */
    SELLER_C,

    /**
     * Buyer confirmed delivery.
     */
    BUYER_C,

    /**
     * Seller and buyer confirmed delivery.
     */
    SELLER_BUYER_C,

    /**
     * Seller reported violation.
     */
    SELLER_RV,

    /**
     * Buyer reported violation.
     */
    BUYER_RV,

    /**
     * Seller and buyer reported violation.
     */
    SELLER_BUYER_RV,

    /**
     * Seller confirmed delivery. Buyer reported violation.
     */
    SELLER_C_BUYER_RV,

    /**
     * Seller reported violation. Buyer confirmed delivery.
     */
    SELLER_RV_BUYER_C;

    public static DeliveryStatus define(int id) {
        return Arrays.stream(DeliveryStatus.values())
                .filter(deliveryStatus -> id == deliveryStatus.ordinal())
                .findFirst()
                .orElse(null);
    }

}