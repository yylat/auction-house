package com.epam.auction.dao.impl;

import com.epam.auction.dao.PhotoDAO;
import com.epam.auction.dao.TableConstant;
import com.epam.auction.entity.Photo;
import com.epam.auction.exception.DAOException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides the base model implementation for `photo` table DAO.
 */
public class PhotoDAOImpl extends GenericDAOImpl<Photo> implements PhotoDAO {

    /**
     * Constructs dao for `photo` table.
     */
    public PhotoDAOImpl() {
        super(TableConstant.PHOTO_QUERY_FIND_ALL,
                TableConstant.PHOTO_QUERY_FIND_BY_ID,
                TableConstant.PHOTO_QUERY_DELETE,
                TableConstant.PHOTO_QUERY_CREATE,
                null);
    }

    @Override
    Photo extractEntity(ResultSet resultSet) throws SQLException {
        return new Photo(
                resultSet.getLong(TableConstant.PHOTO_COLUMN_ID),
                resultSet.getLong(TableConstant.PHOTO_COLUMN_ITEM_ID));
    }

    @Override
    void defineQueryAttributes(Photo entity, PreparedStatement statement) throws SQLException {
        statement.setLong(1, entity.getId());
        statement.setLong(2, entity.getItemId());
    }

    @Override
    public Photo findItemPhoto(int itemId) throws DAOException {
        return findEntity(TableConstant.PHOTO_QUERY_FIND_ITEM_PHOTO,
                statement -> statement.setLong(1, itemId));
    }

    @Override
    public List<Photo> findAll(int itemId) throws DAOException {
        return findSpecificList(TableConstant.PHOTO_QUERY_FIND_ALL_FOR_ITEM,
                statement -> statement.setLong(1, itemId));
    }

    @Override
    public void deleteItemPhotos(long itemId) throws DAOException {
        executeUpdate(TableConstant.PHOTO_QUERY_DELETE_ITEM_PHOTOS,
                statement -> statement.setLong(1, itemId));
    }

}