package com.epam.auction.receiver.impl;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.dao.impl.DAOFactory;
import com.epam.auction.dao.UserDAO;
import com.epam.auction.db.DAOManager;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.AdminReceiver;
import com.epam.auction.receiver.RequestConstant;

class AdminReceiverImpl implements AdminReceiver {

    @Override
    public void updateUserStatus(RequestContent requestContent) throws ReceiverException {
        int userId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.USER_ID)[0]);
        boolean isBanned = Boolean.valueOf(requestContent.getRequestParameter(RequestConstant.IS_BANNED)[0]);

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        DAOManager daoManager = new DAOManager(true, userDAO);

        daoManager.beginTransaction();
        try {
            userDAO.updateUserStatus(isBanned, userId);
            daoManager.commit();
        } catch (DAOException e) {
            daoManager.rollback();
            throw new ReceiverException();
        } finally {
            daoManager.endTransaction();
        }
    }

}