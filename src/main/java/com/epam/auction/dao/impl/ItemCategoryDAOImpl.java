package com.epam.auction.dao.impl;

import com.epam.auction.constant.TableConstant;
import com.epam.auction.dao.ItemCategoryDAO;
import com.epam.auction.entity.ItemCategory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemCategoryDAOImpl extends GenericDAOImpl<ItemCategory> implements ItemCategoryDAO {

    public ItemCategoryDAOImpl() {
        super(TableConstant.ItemCategory.QUERY_FIND_ALL,
                TableConstant.ItemCategory.QUERY_FIND_BY_ID,
                TableConstant.ItemCategory.QUERY_DELETE,
                TableConstant.ItemCategory.QUERY_CREATE,
                TableConstant.ItemCategory.QUERY_UPDATE);
    }

    @Override
    ItemCategory extractEntity(ResultSet resultSet) throws SQLException {
        return new ItemCategory(
                resultSet.getInt(TableConstant.ItemCategory.COLUMN_ID),
                resultSet.getString(TableConstant.ItemCategory.COLUMN_DESCRIPTION),
                resultSet.getInt(TableConstant.ItemCategory.COLUMN_PARENT_CATEGORY_ID));
    }

    @Override
    void defineQueryAttributes(ItemCategory entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getDescription());
        statement.setInt(2, entity.getParentItemCategoryId());
    }

}
