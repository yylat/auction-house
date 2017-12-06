package com.epam.auction.command;

/**
 * Stores page addresses.
 */
public final class PageAddress {

    /**
     * Private constructor without parameters.
     * Restrict instantiation of the class.
     */
    private PageAddress() {
    }

    public static final String ERROR = "/jsp/error.jsp";
    public static final String USER_MANAGEMENT = "/jsp/admin/user_management.jsp";
    public static final String USER_ITEMS = "/jsp/user/user_items.jsp";
    public static final String ITEMS_MANAGEMENT = "/jsp/admin/items_management.jsp";
    public static final String ITEM = "/jsp/item.jsp";
    public static final String ACTIVE_ITEMS = "/jsp/active_items.jsp";
    public static final String USER_BIDS = "/jsp/user/user_bids.jsp";
    public static final String NOTIFICATIONS = "/jsp/user/notifications.jsp";
    public static final String COMING_ITEMS = "/jsp/coming_items.jsp";
    public static final String ACCOUNT = "/jsp/user/account.jsp";
    public static final String PURCHASED_ITEMS = "/jsp/user/purchased_items.jsp";
    public static final String EDIT_ITEM = "/jsp/user/edit_item.jsp";
    public static final String PROFILE = "/jsp/user/profile.jsp";

}