package com.epam.auction.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SiteManager {

    private static final Logger LOGGER = LogManager.getLogger();

    private static final String BUNDLE_NAME = "config.site";

    private static final String UPLOAD_PATH_PROP_NAME = "site.uploadPath";
    private static final String ITEMS_FOR_PAGE_PROP_NAME = "page.items";
    private static final String BIDS_FOR_PAGE_PROP_NAME = "page.bids";
    private static final String NOTIFICATIONS_FOR_PAGE_PROP_NAME = "page.notifications";
    private static final String USERS_FOR_PAGE_PROP_NAME = "page.users";

    private static final String DEFAULT_UPLOAD_PATH = "./auction/photos/";
    private static final int DEFAULT_ITEMS_FOR_PAGE = 8;
    private static final int DEFAULT_BIDS_FOR_PAGE = 20;
    private static final int DEFAULT_NOTIFICATIONS_FOR_PAGE = 20;
    private static final int DEFAULT_USERS_FOR_PAGE = 10;

    private String uploadPath;
    private int itemsForPage;
    private int bidsForPage;
    private int notificationsForPage;
    private int usersForPage;

    private static class Holder {
        private static final SiteManager INSTANCE = new SiteManager();
    }

    private SiteManager() {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);

        try {
            uploadPath = resourceBundle.getString(UPLOAD_PATH_PROP_NAME);
        } catch (MissingResourceException e) {
            LOGGER.log(Level.ERROR, e);
            uploadPath = DEFAULT_UPLOAD_PATH;
        }

        try {
            itemsForPage = Integer.valueOf(resourceBundle.getString(ITEMS_FOR_PAGE_PROP_NAME));
        } catch (MissingResourceException e) {
            LOGGER.log(Level.ERROR, e);
            itemsForPage = DEFAULT_ITEMS_FOR_PAGE;
        }
        try {
            bidsForPage = Integer.valueOf(resourceBundle.getString(BIDS_FOR_PAGE_PROP_NAME));
        } catch (MissingResourceException e) {
            LOGGER.log(Level.ERROR, e);
            bidsForPage = DEFAULT_BIDS_FOR_PAGE;
        }
        try {
            notificationsForPage = Integer.valueOf(resourceBundle.getString(NOTIFICATIONS_FOR_PAGE_PROP_NAME));
        } catch (MissingResourceException e) {
            LOGGER.log(Level.ERROR, e);
            notificationsForPage = DEFAULT_NOTIFICATIONS_FOR_PAGE;
        }
        try {
            usersForPage = Integer.valueOf(resourceBundle.getString(USERS_FOR_PAGE_PROP_NAME));
        } catch (MissingResourceException e) {
            LOGGER.log(Level.ERROR, e);
            usersForPage = DEFAULT_USERS_FOR_PAGE;
        }

    }

    public static SiteManager getInstance() {
        return Holder.INSTANCE;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public int getItemsForPage() {
        return itemsForPage;
    }

    public int getBidsForPage() {
        return bidsForPage;
    }

    public int getNotificationsForPage() {
        return notificationsForPage;
    }

    public int getUsersForPage() {
        return usersForPage;
    }

}