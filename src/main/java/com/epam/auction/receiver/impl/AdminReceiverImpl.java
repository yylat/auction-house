package com.epam.auction.receiver.impl;

import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOException;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.UserDAO;
import com.epam.auction.dao.impl.ItemDAOImpl;
import com.epam.auction.dao.impl.UserDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.AdminReceiver;

public class AdminReceiverImpl implements AdminReceiver {

    private static final String USERS_ATTR = "users";

    @Override
    public void loadUsers(RequestContent requestContent) throws ReceiverException {
        UserDAO userDAO = new UserDAOImpl();

        try (DAOManager daoManager = new DAOManager(userDAO)) {
            requestContent.setRequestAttribute(USERS_ATTR, userDAO.findAll());
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void approveItem(RequestContent requestContent) throws ReceiverException {
        updateItemStatus(requestContent, ItemStatus.CONFIRMED);
    }

    @Override
    public void discardItem(RequestContent requestContent) throws ReceiverException {
        updateItemStatus(requestContent, ItemStatus.NOT_CONFIRMED);
    }

    private void updateItemStatus(RequestContent requestContent, ItemStatus itemStatus) throws ReceiverException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        ItemDAO itemDAO = new ItemDAOImpl();

        DAOManager daoManager = new DAOManager(true, itemDAO);
        daoManager.beginTransaction();

        try {
            if (itemDAO.updateItemStatus(itemId, itemStatus)) {
                daoManager.commit();
            }
        } catch (DAOException e) {
            daoManager.rollback();
            throw new ReceiverException(e);
        } finally {
            daoManager.endTransaction();
        }
    }

}