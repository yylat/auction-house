package com.epam.auction.receiver.impl;

import com.epam.auction.constant.RequestConstant;
import com.epam.auction.content.RequestContent;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.UserDAO;
import com.epam.auction.dao.impl.ItemDAOImpl;
import com.epam.auction.dao.impl.UserDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.AdminReceiver;

public class AdminReceiverImpl implements AdminReceiver {

    private static final String USERS_ATTR = "users";

    @Override
    public void loadUsers(RequestContent requestContent) throws ReceiverLayerException {
        UserDAO userDAO = new UserDAOImpl();

        try (DAOManager daoManager = new DAOManager(userDAO)) {
            requestContent.setRequestAttribute(USERS_ATTR, userDAO.findAll());
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }
    }

    @Override
    public void approveItem(RequestContent requestContent) throws ReceiverLayerException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        ItemDAO itemDAO = new ItemDAOImpl();

        DAOManager daoManager = new DAOManager(true, itemDAO);
        daoManager.beginTransaction();

        try {
            if (itemDAO.approveItem(itemId)) {
                daoManager.commit();
            }
        } catch (DAOLayerException e) {
            daoManager.rollback();
            throw new ReceiverLayerException(e.getMessage(), e);
        } finally {
            daoManager.endTransaction();
        }
    }

}