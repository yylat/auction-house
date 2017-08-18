package com.epam.auction.page;

import java.util.Arrays;

public enum PageName {

    INDEX("/index.jsp"),
    MAIN("/jsp/main.jsp"),
    ERROR("/jsp/error.jsp"),
    MANAGEMENT("/jsp/admin/management.jsp"),
    USER_ITEMS("/jsp/item/user-items.jsp"),
    ITEMS_MANAGEMENT("/jsp/admin/items-management.jsp"),
    ITEM("/jsp/item/item.jsp"),
    ALL_ITEM("/jsp/item/all-item.jsp");

    private final String address;

    PageName(final String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public static PageName getAddress(String address) {
        return Arrays.stream(PageName.values())
                .filter(pageAddress -> pageAddress.address.equals(address))
                .findFirst().orElse(PageName.MAIN);
    }

}