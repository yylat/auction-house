package com.epam.auction.dao;

import com.epam.auction.entity.Photo;
import com.epam.auction.exception.DAOException;

import java.util.List;

public interface PhotoDAO extends GenericDAO<Photo> {

    Photo findItemPhoto(int itemId) throws DAOException;

    List<Photo> findAll(int itemId) throws DAOException;

    boolean deleteItemPhotos(int itemId) throws DAOException;

}