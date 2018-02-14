package com.epam.auction.receiver.impl;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.dao.impl.DAOFactory;
import com.epam.auction.dao.UserDAO;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.util.SiteManager;
import com.epam.auction.receiver.UserReceiver;
import com.epam.auction.util.StringEncoder;
import com.epam.auction.util.MessageProvider;
import com.epam.auction.validator.UserValidator;

import java.util.List;
import java.util.Locale;

class UserReceiverImpl implements UserReceiver {

    @Override
    public void signIn(RequestContent requestContent) throws ReceiverException {
        User user = new User(
                requestContent.getRequestParameter(RequestConstant.USERNAME)[0],
                StringEncoder.encode(requestContent.getRequestParameter(RequestConstant.PASSWORD)[0]));

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

        try (DAOManager daoManager = new DAOManager(userDAO)) {
            if (userDAO.isExist(user) && !user.getIsBanned()) {
                requestContent.setSessionAttribute(RequestConstant.USER, user);
            } else if (user.getIsBanned()) {
                requestContent.setRequestAttribute(RequestConstant.BAN, true);
            } else {
                requestContent.setRequestAttribute(RequestConstant.WRONG_USERNAME_PASSWORD, true);
            }
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void signUp(RequestContent requestContent) throws ReceiverException {
        User user = new User(
                requestContent.getRequestParameter(RequestConstant.USERNAME)[0],
                requestContent.getRequestParameter(RequestConstant.PASSWORD)[0],
                requestContent.getRequestParameter(RequestConstant.LAST_NAME)[0],
                requestContent.getRequestParameter(RequestConstant.FIRST_NAME)[0],
                requestContent.getRequestParameter(RequestConstant.PHONE_NUMBER)[0],
                requestContent.getRequestParameter(RequestConstant.EMAIL)[0]);

        String middleName = requestContent.getRequestParameter(RequestConstant.MIDDLE_NAME)[0];
        if (middleName != null && !middleName.isEmpty()) {
            user.setMiddleName(middleName);
        }

        UserValidator validator = new UserValidator();
        if (validator.validateSignUpParam(user)) {
            user.setPassword(StringEncoder.encode(user.getPassword()));

            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
            DAOManager daoManager = new DAOManager(true, userDAO);

            daoManager.beginTransaction();
            try {

                if (userDAO.isUsernameAlreadyExist(user.getUsername())) {
                    requestContent.setSessionAttribute(RequestConstant.USERNAME_ALREADY_EXIST, true);
                } else if (userDAO.isEmailAlreadyExist(user.getEmail())) {
                    requestContent.setSessionAttribute(RequestConstant.EMAIL_ALREADY_EXIST, true);
                } else {
                    userDAO.create(user);
                    MessageProvider messageProvider =
                            new MessageProvider((Locale) requestContent.getSessionAttribute(RequestConstant.LOCALE));
                    requestContent.setSessionAttribute(RequestConstant.MESSAGE,
                            messageProvider.getMessage(MessageProvider.SUCCESSFUL_REGISTRATION));
                    requestContent.setSessionAttribute(RequestConstant.WAS_SHOWN, false);
                    daoManager.commit();
                }
            } catch (DAOException | MethodNotSupportedException e) {
                daoManager.rollback();
                throw new ReceiverException(e);
            } finally {
                daoManager.endTransaction();
            }


        } else {
            throw new ReceiverException(validator.getValidationMessage());
        }
    }

    @Override
    public void logOut(RequestContent requestContent) {
        requestContent.destroySession();
    }

    @Override
    public void changeUsername(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            String newUsername = requestContent.getRequestParameter(RequestConstant.USERNAME)[0];
            UserValidator userValidator = new UserValidator();

            if (userValidator.validateUsername(newUsername)) {
                user.setUsername(newUsername);

                UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
                DAOManager daoManager = new DAOManager(true, userDAO);

                daoManager.beginTransaction();
                try {
                    if (!userDAO.isUsernameAlreadyExist(newUsername)) {
                        userDAO.update(user);
                        daoManager.commit();
                    } else {
                        requestContent.setSessionAttribute(RequestConstant.USERNAME_ALREADY_EXIST, true);
                    }
                } catch (DAOException | MethodNotSupportedException e) {
                    daoManager.rollback();
                    throw new ReceiverException(e);
                } finally {
                    daoManager.endTransaction();
                }
            } else {
                throw new ReceiverException(userValidator.getValidationMessage());
            }
        }
    }

    @Override
    public void changeEmail(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            String newEmail = requestContent.getRequestParameter(RequestConstant.EMAIL)[0];
            UserValidator userValidator = new UserValidator();

            if (userValidator.validateEmail(newEmail)) {
                user.setEmail(newEmail);

                UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
                DAOManager daoManager = new DAOManager(true, userDAO);

                daoManager.beginTransaction();
                try {
                    if (!userDAO.isEmailAlreadyExist(newEmail)) {
                        userDAO.update(user);
                        daoManager.commit();
                    } else {
                        requestContent.setSessionAttribute(RequestConstant.EMAIL_ALREADY_EXIST, true);
                    }
                } catch (DAOException | MethodNotSupportedException e) {
                    daoManager.rollback();
                    throw new ReceiverException(e);
                } finally {
                    daoManager.endTransaction();
                }
            } else {
                throw new ReceiverException(userValidator.getValidationMessage());
            }
        }
    }

    @Override
    public void changePassword(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            String oldPassword = StringEncoder.encode(requestContent.getRequestParameter(RequestConstant.OLD_PASSWORD)[0]);

            if (oldPassword.equals(user.getPassword())) {
                String newPassword = requestContent.getRequestParameter(RequestConstant.NEW_PASSWORD)[0];

                UserValidator userValidator = new UserValidator();
                if (userValidator.validatePassword(newPassword)) {
                    user.setPassword(StringEncoder.encode(newPassword));

                    UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
                    DAOManager daoManager = new DAOManager(true, userDAO);

                    simpleUserUpdate(user, daoManager, userDAO);
                }
            } else {
                requestContent.setSessionAttribute(RequestConstant.WRONG_PASSWORD, true);
            }

        }
    }

    @Override
    public void updateProfile(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            String newLastName = requestContent.getRequestParameter(RequestConstant.LAST_NAME)[0];
            String newMiddleName = requestContent.getRequestParameter(RequestConstant.MIDDLE_NAME)[0];
            String newFirstName = requestContent.getRequestParameter(RequestConstant.FIRST_NAME)[0];
            String newPhoneNumber = requestContent.getRequestParameter(RequestConstant.PHONE_NUMBER)[0];


            UserValidator userValidator = new UserValidator();
            if (userValidator.validateProfile(newLastName, newMiddleName, newFirstName, newPhoneNumber)) {
                user.setLastName(newLastName);
                user.setMiddleName(newMiddleName);
                user.setFirstName(newFirstName);
                user.setPhoneNumber(newPhoneNumber);

                UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
                DAOManager daoManager = new DAOManager(true, userDAO);

                simpleUserUpdate(user, daoManager, userDAO);
            }
        }
    }

    @Override
    public void loadUsers(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

            try (DAOManager daoManager = new DAOManager(userDAO)) {
                PaginationHelper paginationHelper = new PaginationHelper(SiteManager.getInstance().getUsersForPage());
                paginationHelper.definePage(requestContent);
                if (paginationHelper.pagesNumberNotDefined(requestContent)) {
                    paginationHelper.definePages(requestContent, userDAO.countRows());
                }

                List<User> users = userDAO.findUsersWithLimit(paginationHelper.findOffset(),
                        paginationHelper.getLimit());

                requestContent.setRequestAttribute(RequestConstant.USERS, users);
            } catch (DAOException e) {
                throw new ReceiverException(e);
            }
        }
    }

    @Override
    public void findUsersByUsername(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);
        String username = requestContent.getRequestParameter(RequestConstant.USERNAME)[0];

        if (user != null) {
            UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

            try (DAOManager daoManager = new DAOManager(userDAO)) {
                PaginationHelper paginationHelper = new PaginationHelper(SiteManager.getInstance().getUsersForPage());
                paginationHelper.definePage(requestContent);
                if (paginationHelper.pagesNumberNotDefined(requestContent)) {
                    paginationHelper.definePages(requestContent, userDAO.countRows());
                }

                List<User> users = userDAO.findByUsername(username,
                        paginationHelper.findOffset(), paginationHelper.getLimit());

                requestContent.setRequestAttribute(RequestConstant.USERS, users);
            } catch (DAOException e) {
                throw new ReceiverException(e);
            }
        }
    }

    @Override
    public void showProfile(RequestContent requestContent) throws ReceiverException {
        long sellerId = ((Item) requestContent.getSessionAttribute(RequestConstant.ITEM)).getSellerId();

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

        try (DAOManager daoManager = new DAOManager(userDAO)) {
            requestContent.setRequestAttribute(RequestConstant.PROFILE,
                    userDAO.findEntityById(sellerId));
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    private void simpleUserUpdate(User user, DAOManager daoManager, UserDAO userDAO) throws ReceiverException {
        daoManager.beginTransaction();
        try {
            userDAO.update(user);
            daoManager.commit();
        } catch (DAOException | MethodNotSupportedException e) {
            daoManager.rollback();
            throw new ReceiverException(e);
        } finally {
            daoManager.endTransaction();
        }
    }

}