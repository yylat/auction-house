package com.epam.auction.receiver.impl;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.dao.PhotoDAO;
import com.epam.auction.dao.impl.PhotoDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Photo;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.exception.PhotoLoadingException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.PhotoReceiver;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.util.JSONConverter;
import com.epam.auction.util.PhotoLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PhotoReceiverImpl implements PhotoReceiver {

    @Override
    public void loadPhoto(RequestContent requestContent) throws ReceiverException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        Photo photo;

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            photo = photoDAO.findItemPhoto(itemId);
            if (photo != null) {
                PhotoLoader photoLoader = new PhotoLoader();
                requestContent.setAjaxResponse(photoLoader.loadPhotoAsString(photo.getFileName()));
            }
        } catch (DAOException | PhotoLoadingException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void loadAllPhotos(RequestContent requestContent) throws ReceiverException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        List<String> photosFiles = new ArrayList<>();

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            PhotoLoader photoLoader = new PhotoLoader();
            List<Photo> photos = photoDAO.findAll(itemId);
            if (!photos.isEmpty()) {
                for (Photo photo : photos) {
                    photosFiles.add(photoLoader.loadPhotoAsString(photo.getFileName()));
                }
            }
            requestContent.setAjaxResponse(JSONConverter.objectAsJson(photosFiles));
        } catch (DAOException | PhotoLoadingException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void loadPhotosForDelete(RequestContent requestContent) throws ReceiverException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        Map<Long, String> photos = new HashMap<>();

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            PhotoLoader photoLoader = new PhotoLoader();
            for (Photo photo : photoDAO.findAll(itemId)) {
                photos.put(photo.getId(), photoLoader.loadPhotoAsString(photo.getFileName()));
            }
            requestContent.setAjaxResponse(JSONConverter.objectAsJson(photos));
        } catch (DAOException | PhotoLoadingException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void deletePhotos(RequestContent requestContent) throws ReceiverException {
        String[] photosToDelete = requestContent.getRequestParameter(RequestConstant.PHOTO_ID);

        if (photosToDelete != null) {
            PhotoDAO photoDAO = new PhotoDAOImpl();
            DAOManager daoManager = new DAOManager(true, photoDAO);

            daoManager.beginTransaction();

            try {
                for (String photoForDelete : photosToDelete) {
                    photoDAO.delete(Integer.valueOf(photoForDelete));
                }
                daoManager.commit();
            } catch (DAOException | MethodNotSupportedException e) {
                daoManager.rollback();
                throw new ReceiverException(e);
            } finally {
                daoManager.endTransaction();
            }
        }
    }

}