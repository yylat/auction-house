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

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemReceiverImpl implements ItemReceiver {

    @Override
    public boolean loadUserItems(RequestContent requestContent) throws ReceiverLayerException {
        boolean result = false;

        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(itemDAO)) {
                requestContent.setRequestAttribute(RequestConstant.ITEMS,
                        itemDAO.findAll(user.getId()));
                result = true;
            } catch (DAOLayerException e) {
                throw new ReceiverLayerException(e.getMessage(), e);
            }

        }

        return result;
    }

    @Override
    public boolean loadCategories(RequestContent requestContent) throws ReceiverLayerException {
        ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemCategoryDAO)) {
            requestContent.setAjaxResponse(itemCategoryDAO.findAll());
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }

        return true;
    }

    @Override
    public boolean createItem(RequestContent requestContent) throws ReceiverLayerException {
        boolean result;

        Item item = new Item(
                requestContent.getRequestParameter(RequestConstant.TITLE)[0],
                requestContent.getRequestParameter(RequestConstant.DESCRIPTION)[0],
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.START_PRICE)[0]),
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.BLITZ_PRICE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.START_DATE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.CLOSE_DATE)[0]),
                Integer.valueOf(requestContent.getRequestParameter(RequestConstant.CATEGORY_ID)[0]),
                ((User) requestContent.getSessionAttribute(RequestConstant.USER)).getId());

        List<InputStream> files = requestContent.getFiles();

        ItemDAO itemDAO = new ItemDAOImpl();
        PhotoDAO photoDAO = new PhotoDAOImpl();

        DAOManager daoManager = new DAOManager(true, itemDAO, photoDAO);
        daoManager.beginTransaction();
        try {
            result = itemDAO.create(item);
            int itemId = item.getId();

            if (files != null) {
                List<Photo> photos = files.stream()
                        .map(file -> new Photo(file, itemId))
                        .collect(Collectors.toList());

                for (Photo photo : photos) {
                    result = photoDAO.create(photo);
                }
            }
            daoManager.commit();
        } catch (DAOLayerException e) {
            daoManager.rollback();
            throw new ReceiverLayerException(e.getMessage(), e);
        } finally {
            daoManager.endTransaction();
        }


        return result;
    }

    @Override
    public boolean loadImage(RequestContent requestContent) throws ReceiverLayerException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        Photo photo;

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            photo = photoDAO.findItemPhoto(itemId);
            requestContent.setAjaxFile(photo.getPhotoFile());
        } catch (DAOLayerException | IOException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }

        return true;
    }

    @Override
    public boolean loadCertainItems(RequestContent requestContent) throws ReceiverLayerException {
        boolean result = false;

        String[] statuses = requestContent.getRequestParameter(RequestConstant.ITEM_STATUS);

        if (statuses != null) {

            Stream<ItemStatus> statusesStream = Arrays.stream(ItemStatus.values());
            List<Integer> statusesId = Arrays.stream(statuses)
                    .map(status -> status.toUpperCase().replaceAll("-", "_"))
                    .filter(status -> statusesStream.anyMatch(itemStatus -> itemStatus.toString().equals(status)))
                    .map(status -> ItemStatus.valueOf(status).getId())
                    .collect(Collectors.toList());

            if (!statusesId.isEmpty()) {
                ItemDAO itemDAO = new ItemDAOImpl();

                try (DAOManager daoManager = new DAOManager(itemDAO)) {
                    requestContent.setRequestAttribute(RequestConstant.ITEMS,
                            itemDAO.findCertain(statusesId));
                    result = true;
                } catch (DAOLayerException e) {
                    throw new ReceiverLayerException(e.getMessage(), e);
                }
            }

        }

        return result;
    }

    @Override
    public boolean loadItemsForCheck(RequestContent requestContent) throws ReceiverLayerException {
        return loadItems(requestContent, ItemStatus.CREATED);
    }

    @Override
    public boolean loadActiveItems(RequestContent requestContent) throws ReceiverLayerException {
        return loadItems(requestContent, ItemStatus.ACTIVE);
    }

    @Override
    public boolean loadAllImages(RequestContent requestContent) throws ReceiverLayerException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        List<String> photos = new ArrayList<>();

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            for (Photo photo : photoDAO.findAll(itemId)) {
                photos.add(Converter.inputStreamToString(photo.getPhotoFile()));
            }

            requestContent.setAjaxResponse(photos);
        } catch (DAOLayerException | IOException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }

        return true;
    }

    @Override
    public boolean loadItem(RequestContent requestContent) {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {
            requestContent.setRequestAttribute(RequestConstant.ITEM,
                    itemDAO.findEntityById(itemId));
        } catch (DAOLayerException e) {
            e.printStackTrace();
        }

        return true;
    }

    private boolean loadItems(RequestContent requestContent, ItemStatus itemStatus) throws ReceiverLayerException {
        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {
            requestContent.setRequestAttribute(RequestConstant.ITEMS,
                    itemDAO.findItems(itemStatus));
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }

        return true;
    }

}