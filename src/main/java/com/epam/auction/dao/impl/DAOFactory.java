package com.epam.auction.dao.impl;

import com.epam.auction.dao.BidDAO;
import com.epam.auction.dao.ItemCategoryDAO;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.NotificationDAO;
import com.epam.auction.dao.PhotoDAO;
import com.epam.auction.dao.UserDAO;

public class DAOFactory {

    private final BidDAO bidDAO = new BidDAOImpl();
    private final ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAOImpl();
    private final ItemDAO itemDAO = new ItemDAOImpl();
    private final NotificationDAO notificationDAO = new NotificationDAOImpl();
    private final PhotoDAO photoDAO = new PhotoDAOImpl();
    private final UserDAO userDAO = new UserDAOImpl();

    private static class Holder {
        private static final DAOFactory INSTANCE = new DAOFactory();
    }

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return Holder.INSTANCE;
    }

    public BidDAO getBidDAO() {
        return bidDAO;
    }

    public ItemCategoryDAO getItemCategoryDAO() {
        return itemCategoryDAO;
    }

    public ItemDAO getItemDAO() {
        return itemDAO;
    }

    public NotificationDAO getNotificationDAO() {
        return notificationDAO;
    }

    public PhotoDAO getPhotoDAO() {
        return photoDAO;
    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

}