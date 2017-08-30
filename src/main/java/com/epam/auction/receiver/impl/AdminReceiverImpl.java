package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.impl.ItemDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.AdminReceiver;
import com.epam.auction.receiver.RequestConstant;

public class AdminReceiverImpl implements AdminReceiver {

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