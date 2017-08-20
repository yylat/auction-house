package com.epam.auction.receiver.impl;

import com.epam.auction.constant.RequestConstant;
import com.epam.auction.content.RequestContent;
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
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.ItemReceiver;
import com.epam.auction.util.Converter;
import com.epam.auction.validator.ItemValidator;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ItemReceiverImpl implements ItemReceiver {

    @Override
    public void loadUserItems(RequestContent requestContent) throws ReceiverLayerException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(itemDAO)) {
                requestContent.setRequestAttribute(RequestConstant.ITEMS,
                        itemDAO.findAll(user.getId()));
            } catch (DAOLayerException e) {
                throw new ReceiverLayerException(e.getMessage(), e);
            }
        }
    }

    @Override
    public void loadCategories(RequestContent requestContent) throws ReceiverLayerException {
        ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemCategoryDAO)) {
            requestContent.setAjaxResponse(itemCategoryDAO.findAll());
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }
    }

    @Override
    public void createItem(RequestContent requestContent) throws ReceiverLayerException {
        Item item = new Item(
                requestContent.getRequestParameter(RequestConstant.TITLE)[0],
                requestContent.getRequestParameter(RequestConstant.DESCRIPTION)[0],
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.START_PRICE)[0]),
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.BLITZ_PRICE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.START_DATE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.CLOSE_DATE)[0]),
                Integer.valueOf(requestContent.getRequestParameter(RequestConstant.CATEGORY_ID)[0]),
                ((User) requestContent.getSessionAttribute(RequestConstant.USER)).getId());

        ItemValidator itemValidator = new ItemValidator();
        if (itemValidator.validItemParam(item)) {
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
            } catch (DAOLayerException e) {
                daoManager.rollback();
                throw new ReceiverLayerException(e.getMessage(), e);
            } finally {
                daoManager.endTransaction();
            }
        } else {
            throw new ReceiverLayerException(itemValidator.getValidationMessage());
        }
    }

    @Override
    public void loadImage(RequestContent requestContent) throws ReceiverLayerException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        Photo photo;

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            photo = photoDAO.findItemPhoto(itemId);
            requestContent.setAjaxFile(photo.getPhotoFile());
        } catch (DAOLayerException | IOException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }
    }

    @Override
    public void loadItemsForCheck(RequestContent requestContent) throws ReceiverLayerException {
        loadItems(requestContent, ItemStatus.CREATED);
    }

    @Override
    public void loadActiveItems(RequestContent requestContent) throws ReceiverLayerException {
        loadItems(requestContent, ItemStatus.ACTIVE);
    }

    @Override
    public void loadAllImages(RequestContent requestContent) throws ReceiverLayerException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        List<String> photos = new ArrayList<>();

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            for (Photo photo : photoDAO.findAll(itemId)) {
                photos.add(Converter.inputStreamToString(photo.getPhotoFile()));
            }

            requestContent.setAjaxResponse(Converter.objectToJson(photos));
        } catch (DAOLayerException | IOException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }
    }

    @Override
    public void loadItem(RequestContent requestContent) throws ReceiverLayerException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {
            requestContent.setRequestAttribute(RequestConstant.ITEM,
                    itemDAO.findEntityById(itemId));
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }
    }

    private void loadItems(RequestContent requestContent, ItemStatus itemStatus) throws ReceiverLayerException {
        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {
            requestContent.setRequestAttribute(RequestConstant.ITEMS,
                    itemDAO.findItems(itemStatus));
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }
    }

    private boolean savePhotos(PhotoDAO photoDAO, List<InputStream> files, int itemId) throws DAOLayerException {
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