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

    public static final String ERROR = "/WEB-INF/jsp/error.jsp";
    public static final String USER_MANAGEMENT = "/WEB-INF/jsp/admin/user_management.jsp";
    public static final String USER_ITEMS = "/WEB-INF/jsp/user/user_items.jsp";
    public static final String ITEMS_MANAGEMENT = "/WEB-INF/jsp/admin/items_management.jsp";
    public static final String ITEM = "/WEB-INF/jsp/item.jsp";
    public static final String ACTIVE_ITEMS = "/WEB-INF/jsp/active_items.jsp";
    public static final String USER_BIDS = "/WEB-INF/jsp/user/user_bids.jsp";
    public static final String NOTIFICATIONS = "/WEB-INF/jsp/user/notifications.jsp";
    public static final String COMING_ITEMS = "/WEB-INF/jsp/coming_items.jsp";
    public static final String ACCOUNT = "/WEB-INF/jsp/user/account.jsp";
    public static final String PURCHASED_ITEMS = "/WEB-INF/jsp/user/purchased_items.jsp";
    public static final String EDIT_ITEM = "/WEB-INF/jsp/user/edit_item.jsp";
    public static final String PROFILE = "/WEB-INF/jsp/user/profile.jsp";

}