package com.epam.auction.dao;

import com.epam.auction.entity.Photo;
import com.epam.auction.exception.DAOLayerException;

import java.util.List;

public interface PhotoDAO extends GenericDAO<Photo> {

    Photo findItemPhoto(int itemId) throws DAOLayerException;

    List<Photo> findAll(int itemId) throws DAOLayerException;

}