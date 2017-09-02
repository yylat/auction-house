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
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.NotificationReceiver;
import com.epam.auction.receiver.PaginationHelper;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.receiver.SiteManager;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NotificationReceiverImpl implements NotificationReceiver {

    @Override
    public void loadNotifications(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            NotificationDAO notificationDAO = new NotificationDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(notificationDAO, itemDAO)) {

                PaginationHelper paginationHelper = new PaginationHelper(SiteManager.getInstance().getNotificationsForPage());
                paginationHelper.definePage(requestContent);
                if (!paginationHelper.pagesNumberDefined(requestContent)) {
                    paginationHelper.definePages(requestContent, notificationDAO.countRows(user.getId()));
                }

                List<Notification> notifications = notificationDAO.findUsersNotifications(user.getId(),
                        paginationHelper.findOffset(), paginationHelper.getLimit());

                Map<Notification, Item> notificationItemMap = new LinkedHashMap<>();
                for (Notification notification : notifications) {
                    notificationItemMap.put(notification, itemDAO.findEntityById(notification.getItemId()));
                }

                requestContent.setRequestAttribute(RequestConstant.NOTIFICATION_ITEM_MAP, notificationItemMap);
            } catch (DAOException e) {
                throw new ReceiverException(e);
            }
        }
    }

}