package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.PhotoDAO;
import com.epam.auction.dao.impl.PhotoDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Photo;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.PhotoReceiver;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.util.Converter;

import java.io.IOException;
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
            requestContent.setAjaxResponse(Converter.inputStreamToString(photo.getPhotoFile()));
        } catch (DAOException | IOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void loadAllPhotos(RequestContent requestContent) throws ReceiverException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        List<String> photos = new ArrayList<>();

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            for (Photo photo : photoDAO.findAll(itemId)) {
                photos.add(Converter.inputStreamToString(photo.getPhotoFile()));
            }
            requestContent.setAjaxResponse(Converter.objectToJson(photos));
        } catch (DAOException | IOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void loadPhotosForDelete(RequestContent requestContent) throws ReceiverException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        Map<Integer, String> photos = new HashMap<>();

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            for (Photo photo : photoDAO.findAll(itemId)) {
                photos.put(photo.getId(), Converter.inputStreamToString(photo.getPhotoFile()));
            }
            requestContent.setAjaxResponse(Converter.objectToJson(photos));
        } catch (DAOException | IOException e) {
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