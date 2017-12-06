package com.epam.auction.receiver.impl;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.dao.impl.DAOFactory;
import com.epam.auction.dao.ItemCategoryDAO;
import com.epam.auction.db.DAOManager;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.ItemCategoryReceiver;
import com.epam.auction.util.JSONConverter;

class ItemCategoryReceiverImpl implements ItemCategoryReceiver {

    @Override
    public void loadCategories(RequestContent requestContent) throws ReceiverException {
        ItemCategoryDAO itemCategoryDAO = DAOFactory.getInstance().getItemCategoryDAO();

        try (DAOManager daoManager = new DAOManager(itemCategoryDAO)) {
            requestContent.setAjaxResponse(JSONConverter.objectAsJson(itemCategoryDAO.findAll()));
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

}