package com.epam.auction.receiver;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.exception.ReceiverException;

/**
 * Provides the base model interface for load and delete photos.
 */
public interface PhotoReceiver extends Receiver {

    /**
     * Loads photo of the item.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not load photo
     */
    void loadPhoto(RequestContent requestContent) throws ReceiverException;

    /**
     * Loads all photos of the item.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not load photos
     */
    void loadAllPhotos(RequestContent requestContent) throws ReceiverException;

    /**
     * Loads all photos of the item with their's ids.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not load photos
     */
    void loadPhotosForDelete(RequestContent requestContent) throws ReceiverException;

    /**
     * Deletes photos.
     *
     * @param requestContent request content
     * @throws ReceiverException if can not delete photos
     */
    void deletePhotos(RequestContent requestContent) throws ReceiverException;

}