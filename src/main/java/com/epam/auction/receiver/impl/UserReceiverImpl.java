package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.UserDAO;
import com.epam.auction.dao.impl.UserDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.receiver.UserReceiver;
import com.epam.auction.util.Encoder;
import com.epam.auction.util.MessageProvider;
import com.epam.auction.validator.UserValidator;

import java.math.BigDecimal;
import java.util.Locale;

public class UserReceiverImpl implements UserReceiver {

    @Override
    public void signIn(RequestContent requestContent) throws ReceiverLayerException {
        User user = new User(
                requestContent.getRequestParameter(RequestConstant.USERNAME)[0],
                Encoder.encode(requestContent.getRequestParameter(RequestConstant.PASSWORD)[0]));

        UserDAO userDAO = new UserDAOImpl();

        try (DAOManager daoManager = new DAOManager(userDAO)) {
            if (userDAO.isExist(user)) {
                requestContent.setSessionAttribute(RequestConstant.USER, user);
            } else {
                requestContent.setRequestAttribute(RequestConstant.WRONG_USERNAME_PASSWORD, true);
            }
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e.getMessage(), e);
        }
    }

    @Override
    public void signUp(RequestContent requestContent) throws ReceiverLayerException {
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
                    daoManager.commit();
                }

                requestContent.setSessionAttribute(RequestConstant.WAS_SHOWN, false);
            } catch (DAOLayerException e) {
                daoManager.rollback();
                throw new ReceiverLayerException(e.getMessage(), e);
            } finally {
                daoManager.endTransaction();
            }


        } else {
            throw new ReceiverLayerException(validator.getValidationMessage());
        }
    }

    @Override
    public void logOut(RequestContent requestContent) {
        requestContent.destroySessionAttributes();
    }

    @Override
    public void replenishBalance(RequestContent requestContent) throws ReceiverLayerException {
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
            } catch (DAOLayerException | MethodNotSupportedException e) {
                daoManager.rollback();
                throw new ReceiverLayerException(e.getMessage(), e);
            } finally {
                daoManager.endTransaction();
            }
            requestContent.setSessionAttribute(RequestConstant.USER, user);
        }

    }

}