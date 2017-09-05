package com.epam.auction.dao;

import com.epam.auction.entity.Photo;
import com.epam.auction.exception.DAOException;

import java.util.List;

/**
 * Provides the base model DAO interface for `photo` table.
 */
public interface PhotoDAO extends GenericDAO<Photo> {

    /**
     * Returns the photo with item id.
     *
     * @param itemId item id
     * @return the photo with item id
     * @throws DAOException if SQL exception occurred
     */
    Photo findItemPhoto(int itemId) throws DAOException;

    /**
     * Returns all photos with item id.
     *
     * @param itemId item id
     * @return all photos with item id
     * @throws DAOException if SQL exception occurred
     */
    List<Photo> findAll(int itemId) throws DAOException;

    /**
     * Deletes all photos with item id.
     *
     * @param itemId item id
     * @throws DAOException if SQL exception occurred
     */
    void deleteItemPhotos(long itemId) throws DAOException;

}