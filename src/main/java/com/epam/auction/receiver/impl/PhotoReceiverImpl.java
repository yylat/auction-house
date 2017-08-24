package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.PhotoDAO;
import com.epam.auction.dao.impl.PhotoDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Photo;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.PhotoReceiver;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.util.Converter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PhotoReceiverImpl implements PhotoReceiver {
    @Override
    public void loadPhoto(RequestContent requestContent) throws ReceiverLayerException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        PhotoDAO photoDAO = new PhotoDAOImpl();
        Photo photo;

        try (DAOManager daoManager = new DAOManager(photoDAO)) {
            photo = photoDAO.findItemPhoto(itemId);
            requestContent.setAjaxResponse(Converter.inputStreamToString(photo.getPhotoFile()));
        } catch (DAOLayerException | IOException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }
    }

    @Override
    public void loadAllPhotos(RequestContent requestContent) throws ReceiverLayerException {
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
}
