package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.NotificationDAO;
import com.epam.auction.dao.impl.ItemDAOImpl;
import com.epam.auction.dao.impl.NotificationDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.Notification;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.NotificationReceiver;
import com.epam.auction.receiver.RequestConstant;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NotificationReceiverImpl implements NotificationReceiver {

    private final static int notificationsForPage = 20;

    @Override
    public void loadNotifications(RequestContent requestContent) throws ReceiverLayerException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);
        String[] pages = requestContent.getRequestParameter(RequestConstant.PAGES);

        int pageToGo;
        String[] page = requestContent.getRequestParameter(RequestConstant.PAGE);
        if (page != null) {
            pageToGo = Integer.valueOf(page[0]);
        } else {
            pageToGo = 1;
        }

        if (user != null) {
            NotificationDAO notificationDAO = new NotificationDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(notificationDAO, itemDAO)) {
                if (pages != null) {
                    requestContent.setRequestAttribute(RequestConstant.PAGES, pages[0]);
                } else {
                    requestContent.setRequestAttribute(RequestConstant.PAGES,
                            (itemDAO.countRows(user.getId()) / notificationsForPage) + 1);
                }
                requestContent.setRequestAttribute(RequestConstant.PAGE, pageToGo);

                List<Notification> notifications = notificationDAO.findUsersNotifications(user.getId(), (pageToGo - 1) * notificationsForPage, notificationsForPage);

                Map<Notification, Item> notificationItemMap = new LinkedHashMap<>();
                for (Notification notification : notifications) {
                    notificationItemMap.put(notification, itemDAO.findEntityById(notification.getItemId()));
                }

                requestContent.setRequestAttribute(RequestConstant.NOTIFICATION_ITEM_MAP, notificationItemMap);
            } catch (DAOLayerException e) {
                throw new ReceiverLayerException(e.getMessage(), e);
            }
        }
    }

}