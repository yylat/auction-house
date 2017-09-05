package com.epam.auction.dao.impl;

import com.epam.auction.dao.TableConstant;
import com.epam.auction.dao.ItemCategoryDAO;
import com.epam.auction.entity.ItemCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Provides the base model implementation for `item_category` table DAO.
 */
public class ItemCategoryDAOImpl extends GenericDAOImpl<ItemCategory> implements ItemCategoryDAO {

    /**
     * Constructs dao for `item_category` table.
     */
    public ItemCategoryDAOImpl() {
        super(TableConstant.ITEM_CATEGORY_QUERY_FIND_ALL,
                TableConstant.ITEM_CATEGORY_QUERY_FIND_BY_ID,
                TableConstant.ITEM_CATEGORY_QUERY_DELETE,
                TableConstant.ITEM_CATEGORY_QUERY_CREATE,
                TableConstant.ITEM_CATEGORY_QUERY_UPDATE);
    }

    @Override
    ItemCategory extractEntity(ResultSet resultSet) throws SQLException {
        return new ItemCategory(
                resultSet.getLong(TableConstant.ITEM_CATEGORY_COLUMN_ID),
                resultSet.getString(TableConstant.ITEM_CATEGORY_COLUMN_DESCRIPTION),
                resultSet.getLong(TableConstant.ITEM_CATEGORY_COLUMN_PARENT_CATEGORY_ID));
    }

    @Override
    void defineQueryAttributes(ItemCategory entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getDescription());
        statement.setLong(2, entity.getParentItemCategoryId());
    }

}