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

        if (user != null) {
            NotificationDAO notificationDAO = new NotificationDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(notificationDAO, itemDAO)) {
                List<Notification> notifications;

                String[] lastNotificationIdOb = requestContent.getRequestParameter(RequestConstant.LAST_ITEM_ID);
                String[] firstNotificationOb = requestContent.getRequestParameter(RequestConstant.FIRST_ITEM_ID);

                if (lastNotificationIdOb != null) {
                    notifications = notificationDAO.findNextUserNotifications(user.getId(), Integer.valueOf(lastNotificationIdOb[0]), notificationsForPage + 1);
                    requestContent.setRequestAttribute(RequestConstant.FIRST_ITEM_ID, notifications.get(0).getId());
                    requestContent.setRequestAttribute(RequestConstant.LAST_ITEM_ID,
                            hasMore(requestContent, notifications));
                } else if (firstNotificationOb != null) {
                    notifications = notificationDAO.findPrevUserNotifications(user.getId(), Integer.valueOf(firstNotificationOb[0]), notificationsForPage + 1);
                    requestContent.setRequestAttribute(RequestConstant.FIRST_ITEM_ID,
                            hasMore(requestContent, notifications));
                    requestContent.setRequestAttribute(RequestConstant.HAS_NEXT, true);
                } else {
                    notifications = notificationDAO.findUserNotifications(user.getId(), notificationsForPage + 1);
                    requestContent.setRequestAttribute(RequestConstant.FIRST_ITEM_ID, null);
                    requestContent.setRequestAttribute(RequestConstant.LAST_ITEM_ID,
                            hasMore(requestContent, notifications));
                }

                Map<Notification, Item> notificationItemMap = new LinkedHashMap<>();

                for (Notification notification : notifications) {
                    notificationItemMap.put(notification,
                            itemDAO.findEntityById(notification.getItemId()));
                }

                requestContent.setRequestAttribute(RequestConstant.NOTIFICATION_ITEM_MAP, notificationItemMap);
            } catch (DAOLayerException e) {
                throw new ReceiverLayerException(e.getMessage(), e);
            }
        }
    }

    private Integer hasMore(RequestContent requestContent, List<Notification> notifications) {
        if ((notificationsForPage + 1) == notifications.size()) {
            notifications = notifications.subList(0, notificationsForPage);
            return notifications.get(notificationsForPage + 1).getId();
        } else {
            return null;
        }
    }

}