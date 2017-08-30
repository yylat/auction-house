package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.ItemCategoryDAO;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.PhotoDAO;
import com.epam.auction.dao.impl.ItemCategoryDAOImpl;
import com.epam.auction.dao.impl.ItemDAOImpl;
import com.epam.auction.dao.impl.PhotoDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.entity.Photo;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.ItemReceiver;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.util.Converter;
import com.epam.auction.util.DateFixer;
import com.epam.auction.validator.ItemValidator;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ItemReceiverImpl implements ItemReceiver {

    private final static int itemsForPage = 8;

    @Override
    public void loadCategories(RequestContent requestContent) throws ReceiverException {
        ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemCategoryDAO)) {
            requestContent.setAjaxResponse(Converter.objectToJson(itemCategoryDAO.findAll()));
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void createItem(RequestContent requestContent) throws ReceiverException {
        Item item = new Item(
                requestContent.getRequestParameter(RequestConstant.TITLE)[0],
                requestContent.getRequestParameter(RequestConstant.DESCRIPTION)[0],
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.START_PRICE)[0]),
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.BLITZ_PRICE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.START_DATE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.CLOSE_DATE)[0]),
                Integer.valueOf(requestContent.getRequestParameter(RequestConstant.CATEGORY)[0]),
                ((User) requestContent.getSessionAttribute(RequestConstant.USER)).getId());

        ItemValidator itemValidator = new ItemValidator();
        if (itemValidator.validateItemParam(item)) {
            List<InputStream> files = requestContent.getFiles();

            ItemDAO itemDAO = new ItemDAOImpl();
            PhotoDAO photoDAO = new PhotoDAOImpl();

            DAOManager daoManager = new DAOManager(true, itemDAO, photoDAO);
            daoManager.beginTransaction();
            try {
                boolean itemCreated = itemDAO.create(item);
                boolean photosSaved = true;
                if (itemCreated && files != null) {
                    photosSaved = savePhotos(photoDAO, files, item.getId());
                }
                if (photosSaved) {
                    daoManager.commit();
                }
            } catch (DAOException e) {
                daoManager.rollback();
                throw new ReceiverException(e);
            } finally {
                daoManager.endTransaction();
            }
        } else {
            throw new ReceiverException(itemValidator.getValidationMessage());
        }
    }

    @Override
    public void loadItem(RequestContent requestContent) throws ReceiverException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {
            requestContent.setSessionAttribute(RequestConstant.ITEM,
                    itemDAO.findEntityById(itemId));
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void updateItem(RequestContent requestContent) throws ReceiverException {
        String newTitle = requestContent.getRequestParameter(RequestConstant.TITLE)[0];
        String newDescription = requestContent.getRequestParameter(RequestConstant.DESCRIPTION)[0];
        BigDecimal newStartPrice = new BigDecimal(requestContent.getRequestParameter(RequestConstant.START_PRICE)[0]);
        BigDecimal newBlitzPrice = new BigDecimal(requestContent.getRequestParameter(RequestConstant.BLITZ_PRICE)[0]);
        Date newStartDate = Date.valueOf(requestContent.getRequestParameter(RequestConstant.START_DATE)[0]);
        Date newCloseDate = Date.valueOf(requestContent.getRequestParameter(RequestConstant.CLOSE_DATE)[0]);

        ItemValidator itemValidator = new ItemValidator();

        if (itemValidator.validateItemParam(newTitle, newStartPrice, newBlitzPrice, newStartDate, newCloseDate)) {
            Item item = (Item) requestContent.getSessionAttribute(RequestConstant.ITEM);
            item.setName(newTitle);
            item.setDescription(newDescription);
            item.setStartPrice(newStartPrice);
            item.setBlitzPrice(newBlitzPrice);
            item.setStartDate(newStartDate);
            item.setCloseDate(newCloseDate);

            ItemDAO itemDAO = new ItemDAOImpl();
            DAOManager daoManager = new DAOManager(true, itemDAO);

            daoManager.beginTransaction();
            try {
                updateItemForCheck(item, itemDAO);
                daoManager.commit();
            } catch (DAOException | MethodNotSupportedException e) {
                daoManager.rollback();
                throw new ReceiverException(e);
            } finally {
                daoManager.endTransaction();
            }


        }

    }

    @Override
    public void addPhotos(RequestContent requestContent) throws ReceiverException {
        Item item = (Item) requestContent.getSessionAttribute(RequestConstant.ITEM);

        List<InputStream> files = requestContent.getFiles();
        if (files != null) {

            PhotoDAO photoDAO = new PhotoDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();
            DAOManager daoManager = new DAOManager(true, photoDAO, itemDAO);

            daoManager.beginTransaction();
            try {
                savePhotos(photoDAO, files, item.getId());
                updateItemForCheck(item, itemDAO);
                daoManager.commit();
            } catch (DAOException | MethodNotSupportedException e) {
                daoManager.rollback();
                throw new ReceiverException(e);
            } finally {
                daoManager.endTransaction();
            }
        }
    }

    @Override
    public void deleteItem(RequestContent requestContent) throws ReceiverException {
        int itemId = ((Item) requestContent.getSessionAttribute(RequestConstant.ITEM)).getId();

        ItemDAO itemDAO = new ItemDAOImpl();
        PhotoDAO photoDAO = new PhotoDAOImpl();
        DAOManager daoManager = new DAOManager(true, itemDAO, photoDAO);

        daoManager.beginTransaction();
        try {
            photoDAO.deleteItemPhotos(itemId);
            itemDAO.delete(itemId);
            daoManager.commit();
        } catch (DAOException | MethodNotSupportedException e) {
            daoManager.rollback();
            throw new ReceiverException(e);
        } finally {
            daoManager.endTransaction();
        }
    }

    private void updateItemForCheck(Item item, ItemDAO itemDAO) throws MethodNotSupportedException, DAOException {
        ItemValidator itemValidator = new ItemValidator();
        if (!itemValidator.validateStartDate(item.getStartDate())) {
            item.setStartDate(DateFixer.fix(item.getStartDate()));
        }
        if (!itemValidator.validateStartDate(item.getCloseDate())) {
            item.setCloseDate(DateFixer.fix(item.getCloseDate()));
        }
        item.setStatus(ItemStatus.CREATED);
        itemDAO.update(item);
    }

    private boolean savePhotos(PhotoDAO photoDAO, List<InputStream> files, int itemId) throws DAOException {
        boolean result = false;
        List<Photo> photos = files.stream()
                .map(file -> new Photo(file, itemId))
                .collect(Collectors.toList());

        for (Photo photo : photos) {
            result = photoDAO.create(photo);
            if (!result) {
                return false;
            }
        }
        return result;
    }

}