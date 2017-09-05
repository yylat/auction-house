package com.epam.auction.dao.impl;

import com.epam.auction.dao.TableConstant;
import com.epam.auction.dao.UserDAO;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDAOImpl extends GenericDAOImpl<User> implements UserDAO {

    public UserDAOImpl() {
        super(TableConstant.USER_QUERY_FIND_ALL,
                TableConstant.USER_QUERY_FIND_BY_ID,
                null,
                TableConstant.USER_QUERY_CREATE,
                TableConstant.USER_QUERY_UPDATE);
    }

    @Override
    public void delete(long id) throws DAOException, MethodNotSupportedException {
        throw new MethodNotSupportedException();
    }

    @Override
    User extractEntity(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong(TableConstant.USER_COLUMN_ID),
                resultSet.getString(TableConstant.USER_COLUMN_USERNAME),
                resultSet.getString(TableConstant.USER_COLUMN_PASSWORD),
                resultSet.getString(TableConstant.USER_COLUMN_LAST_NAME),
                resultSet.getString(TableConstant.USER_COLUMN_MIDDLE_NAME),
                resultSet.getString(TableConstant.USER_COLUMN_FIRST_NAME),
                resultSet.getString(TableConstant.USER_COLUMN_PHONE_NUMBER),
                resultSet.getString(TableConstant.USER_COLUMN_EMAIL),
                resultSet.getBigDecimal(TableConstant.USER_COLUMN_BALANCE),
                resultSet.getBoolean(TableConstant.USER_COLUMN_IS_BANNED),
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
        statement.setBoolean(9, entity.isBanned());
        statement.setInt(10, entity.getRole().ordinal());
    }

    @Override
    public boolean isExist(User user) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(TableConstant.USER_QUERY_IS_EXIST)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getLong(TableConstant.USER_COLUMN_ID));
                user.setLastName(resultSet.getString(TableConstant.USER_COLUMN_LAST_NAME));
                user.setMiddleName(resultSet.getString(TableConstant.USER_COLUMN_MIDDLE_NAME));
                user.setFirstName(resultSet.getString(TableConstant.USER_COLUMN_FIRST_NAME));
                user.setPhoneNumber(resultSet.getString(TableConstant.USER_COLUMN_PHONE_NUMBER));
                user.setEmail(resultSet.getString(TableConstant.USER_COLUMN_EMAIL));
                user.setBalance(resultSet.getBigDecimal(TableConstant.USER_COLUMN_BALANCE));
                user.setBanned(resultSet.getBoolean(TableConstant.USER_COLUMN_IS_BANNED));
                user.setRole(User.UserRole.define(resultSet.getInt(TableConstant.USER_COLUMN_USER_ROLE_ID)));

                result = true;
            }

        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

    public boolean isUsernameAlreadyExist(String username) throws DAOException {
        return isAlreadyExist(username, TableConstant.USER_QUERY_IS_EXIST_USERNAME);
    }

    public boolean isEmailAlreadyExist(String email) throws DAOException {
        return isAlreadyExist(email, TableConstant.USER_QUERY_IS_EXIST_EMAIL);
    }

    @Override
    public int countRows() throws DAOException {
        return countRows(TableConstant.USER_QUERY_FIND_ROWS_COUNT_USERS, statement -> {
        });
    }

    @Override
    public List<User> findUsersWithLimit(int offset, int limit) throws DAOException {
        return findSpecificList(TableConstant.USER_QUERY_FIND_USERS, statement -> {
            statement.setInt(1, offset);
            statement.setInt(2, limit);
        });
    }

    @Override
    public void updateUserStatus(boolean isBanned, int userId) throws DAOException {
        executeUpdate(TableConstant.USER_QUERY_UPDATE_STATUS, statement -> {
            statement.setBoolean(1, isBanned);
            statement.setLong(2, userId);
        });
    }

    @Override
    public int countRows(String username) throws DAOException {
        return countRows(TableConstant.USER_QUERY_FIND_ROWS_COUNT_USERNAME, statement -> {
            statement.setString(1, "%" + username + "%");
        });
    }

    @Override
    public List<User> findByUsername(String username, int offset, int limit) throws DAOException {
        return findSpecificList(TableConstant.USER_QUERY_FIND_BY_USERNAME, statement -> {
            statement.setString(1, "%" + username + "%");
            statement.setInt(2, offset);
            statement.setInt(3, limit);
        });
    }

    private boolean isAlreadyExist(String parameter, String query) throws DAOException {
        boolean result = false;
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, parameter);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                result = resultSet.getBoolean(1);
            }
        } catch (SQLException e) {
            throw new DAOException(e);
        }
        return result;
    }

}