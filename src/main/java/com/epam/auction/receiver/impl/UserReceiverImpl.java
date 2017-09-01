package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.UserDAO;
import com.epam.auction.dao.impl.UserDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.receiver.UserReceiver;
import com.epam.auction.util.Encoder;
import com.epam.auction.util.MessageProvider;
import com.epam.auction.validator.UserValidator;

import java.math.BigDecimal;
import java.util.Locale;

public class UserReceiverImpl implements UserReceiver {

    @Override
    public void signIn(RequestContent requestContent) throws ReceiverException {
        User user = new User(
                requestContent.getRequestParameter(RequestConstant.USERNAME)[0],
                Encoder.encode(requestContent.getRequestParameter(RequestConstant.PASSWORD)[0]));

        UserDAO userDAO = new UserDAOImpl();

        try (DAOManager daoManager = new DAOManager(userDAO)) {
            if (userDAO.isExist(user)) {
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
                requestContent.getRequestParameter(RequestConstant.MIDDLE_NAME)[0],
                requestContent.getRequestParameter(RequestConstant.FIRST_NAME)[0],
                requestContent.getRequestParameter(RequestConstant.PHONE_NUMBER)[0],
                requestContent.getRequestParameter(RequestConstant.EMAIL)[0]);

        UserValidator validator = new UserValidator();
        if (validator.validateSignUpParam(user)) {
            user.setPassword(Encoder.encode(user.getPassword()));

            UserDAO userDAO = new UserDAOImpl();
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
            } catch (DAOException e) {
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
    public void replenishBalance(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            BigDecimal sumToAdd = new BigDecimal(requestContent.getRequestParameter(RequestConstant.MONEY_AMOUNT)[0]);

            UserDAO userDAO = new UserDAOImpl();
            DAOManager daoManager = new DAOManager(true, userDAO);

            daoManager.beginTransaction();
            try {
                user.setBalance(user.getBalance().add(sumToAdd));
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

    @Override
    public void changeUsername(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            String newUsername = requestContent.getRequestParameter(RequestConstant.USERNAME)[0];
            UserValidator userValidator = new UserValidator();

            if (userValidator.validateUsername(newUsername)) {
                user.setUsername(newUsername);

                UserDAO userDAO = new UserDAOImpl();
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

                UserDAO userDAO = new UserDAOImpl();
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
            String oldPassword = Encoder.encode(requestContent.getRequestParameter(RequestConstant.OLD_PASSWORD)[0]);

            if (oldPassword.equals(user.getPassword())) {
                String newPassword = requestContent.getRequestParameter(RequestConstant.NEW_PASSWORD)[0];

                UserValidator userValidator = new UserValidator();
                if (userValidator.validatePassword(newPassword)) {
                    user.setPassword(Encoder.encode(newPassword));

                    UserDAO userDAO = new UserDAOImpl();
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

                UserDAO userDAO = new UserDAOImpl();
                DAOManager daoManager = new DAOManager(true, userDAO);

                simpleUserUpdate(user, daoManager, userDAO);
            }
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