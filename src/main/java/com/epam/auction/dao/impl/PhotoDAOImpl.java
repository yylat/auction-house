package com.epam.auction.dao.impl;

import com.epam.auction.dao.TableConstant;
import com.epam.auction.dao.PhotoDAO;
import com.epam.auction.entity.Photo;
import com.epam.auction.exception.DAOLayerException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhotoDAOImpl extends GenericDAOImpl<Photo> implements PhotoDAO {

    public PhotoDAOImpl() {
        super(TableConstant.PHOTO_QUERY_FIND_ALL,
                TableConstant.PHOTO_QUERY_FIND_BY_ID,
                TableConstant.PHOTO_QUERY_DELETE,
                TableConstant.PHOTO_QUERY_CREATE,
                TableConstant.PHOTO_QUERY_UPDATE);
    }

    @Override
    Photo extractEntity(ResultSet resultSet) throws SQLException {
        return new Photo(
                resultSet.getInt(TableConstant.PHOTO_COLUMN_ID),
                resultSet.getBinaryStream(TableConstant.PHOTO_COLUMN_PHOTO_FILE),
                resultSet.getInt(TableConstant.PHOTO_COLUMN_ITEM_ID));
    }

    @Override
    void defineQueryAttributes(Photo entity, PreparedStatement statement) throws SQLException {
        statement.setBinaryStream(1, entity.getPhotoFile());
        statement.setInt(2, entity.getItemId());
    }

    @Override
    public Photo findItemPhoto(int itemId) throws DAOLayerException {
        Photo photo = null;

        try (PreparedStatement statement = connection.prepareStatement(TableConstant.PHOTO_QUERY_FIND_ITEM_PHOTO)) {
            statement.setInt(1, itemId);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                photo = extractEntity(resultSet);
            }

        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }

        return photo;
    }

    @Override
    public List<Photo> findAll(int itemId) throws DAOLayerException {
        List<Photo> photos;

        try (PreparedStatement statement = connection.prepareStatement(TableConstant.PHOTO_QUERY_FIND_ALL_FOR_ITEM)) {
            statement.setInt(1, itemId);

            ResultSet resultSet = statement.executeQuery();
            photos = new ArrayList<>();
            while (resultSet.next()) {
                photos.add(extractEntity(resultSet));
            }

        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }

        return photos;
    }

}