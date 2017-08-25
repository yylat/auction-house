package com.epam.auction.dao.impl;

import com.epam.auction.dao.TableConstant;
import com.epam.auction.dao.UserDAO;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOLayerException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO {

    public UserDAOImpl() {
        super(TableConstant.USER_QUERY_FIND_ALL,
                TableConstant.USER_QUERY_FIND_BY_ID,
                TableConstant.USER_QUERY_DELETE,
                TableConstant.USER_QUERY_CREATE,
                TableConstant.USER_QUERY_UPDATE);
    }

    @Override
    User extractEntity(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getInt(TableConstant.USER_COLUMN_ID),
                resultSet.getString(TableConstant.USER_COLUMN_USERNAME),
                resultSet.getString(TableConstant.USER_COLUMN_PASSWORD),
                resultSet.getString(TableConstant.USER_COLUMN_LAST_NAME),
                resultSet.getString(TableConstant.USER_COLUMN_MIDDLE_NAME),
                resultSet.getString(TableConstant.USER_COLUMN_FIRST_NAME),
                resultSet.getString(TableConstant.USER_COLUMN_PHONE_NUMBER),
                resultSet.getString(TableConstant.USER_COLUMN_EMAIL),
                resultSet.getBigDecimal(TableConstant.USER_COLUMN_BALANCE),
                resultSet.getBoolean(TableConstant.USER_COLUMN_IS_DELETED),
                User.UserRole.define(resultSet.getInt(TableConstant.USER_COLUMN_USER_ROLE_ID)));
    }

    @Override
    void defineQueryAttributes(User entity, PreparedStatement statement) throws SQLException {
        statement.setString(1, entity.getUsername());
        statement.setString(2, entity.getPassword());
        statement.setString(3, entity.getLastName());
        statement.setString(4, entity.getMiddleName());
        statement.setString(5, entity.getFirstName());
        statement.setString(6, entity.getPhoneNumber());
        statement.setString(7, entity.getEmail());
        statement.setBigDecimal(8, entity.getBalance());
        statement.setBoolean(9, entity.isDeleted());
        statement.setInt(10, entity.getRole().ordinal());
    }

    @Override
    public boolean isExist(User user) throws DAOLayerException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(TableConstant.USER_QUERY_IS_EXIST)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(TableConstant.USER_COLUMN_ID));
                user.setLastName(resultSet.getString(TableConstant.USER_COLUMN_LAST_NAME));
                user.setMiddleName(resultSet.getString(TableConstant.USER_COLUMN_MIDDLE_NAME));
                user.setFirstName(resultSet.getString(TableConstant.USER_COLUMN_FIRST_NAME));
                user.setPhoneNumber(resultSet.getString(TableConstant.USER_COLUMN_PHONE_NUMBER));
                user.setEmail(resultSet.getString(TableConstant.USER_COLUMN_EMAIL));
                user.setBalance(resultSet.getBigDecimal(TableConstant.USER_COLUMN_BALANCE));
                user.setDeleted(resultSet.getBoolean(TableConstant.USER_COLUMN_IS_DELETED));
                user.setRole(User.UserRole.define(resultSet.getInt(TableConstant.USER_COLUMN_USER_ROLE_ID)));

                result = true;
            }

        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }
        return result;
    }

    public boolean isUsernameAlreadyExist(String username) throws DAOLayerException {
        return isAlreadyExist(username, TableConstant.USER_QUERY_IS_EXIST_USERNAME);
    }

    public boolean isEmailAlreadyExist(String email) throws DAOLayerException {
        return isAlreadyExist(email, TableConstant.USER_QUERY_IS_EXIST_EMAIL);
    }

    private boolean isAlreadyExist(String parameter, String query) throws DAOLayerException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DAOLayerException(e.getMessage(), e);
        }
        return result;
    }

}