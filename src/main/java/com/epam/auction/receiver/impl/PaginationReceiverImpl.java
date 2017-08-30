package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.BidDAO;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.NotificationDAO;
import com.epam.auction.dao.filter.FilterCriteria;
import com.epam.auction.dao.filter.FilterQueryParameter;
import com.epam.auction.dao.filter.OrderCriteria;
import com.epam.auction.dao.impl.BidDAOImpl;
import com.epam.auction.dao.impl.ItemDAOImpl;
import com.epam.auction.dao.impl.NotificationDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Bid;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.entity.Notification;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.exception.WrongFilterParameterException;
import com.epam.auction.receiver.PageParameter;
import com.epam.auction.receiver.PaginationReceiver;
import com.epam.auction.receiver.RequestConstant;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class PaginationReceiverImpl implements PaginationReceiver {

    @Override
    public void loadBids(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            BidDAO bidDAO = new BidDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(bidDAO, itemDAO)) {
                if (!definePagesNumber(requestContent)) {
                    requestContent.setRequestAttribute(RequestConstant.PAGES,
                            (itemDAO.countRows(user.getId()) / PageParameter.BIDS_FOR_PAGE) + 1);
                }

                List<Bid> bids = bidDAO.findUsersBids(user.getId(),
                        defineOffset(requestContent, PageParameter.BIDS_FOR_PAGE),
                        PageParameter.BIDS_FOR_PAGE);

                Map<Bid, Item> bidItemMap = new LinkedHashMap<>();
                for (Bid bid : bids) {
                    bidItemMap.put(bid, itemDAO.findEntityById(bid.getItemId()));
                }

                requestContent.setRequestAttribute(RequestConstant.BID_ITEM_MAP, bidItemMap);
            } catch (DAOException e) {
                throw new ReceiverException(e);
            }
        }
    }

    @Override
    public void loadNotifications(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            NotificationDAO notificationDAO = new NotificationDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(notificationDAO, itemDAO)) {
                if (!definePagesNumber(requestContent)) {
                    requestContent.setRequestAttribute(RequestConstant.PAGES,
                            (itemDAO.countRows(user.getId()) / PageParameter.NOTIFICATIONS_FOR_PAGE) + 1);
                }

                List<Notification> notifications = notificationDAO.findUsersNotifications(user.getId(),
                        defineOffset(requestContent, PageParameter.NOTIFICATIONS_FOR_PAGE),
                        PageParameter.NOTIFICATIONS_FOR_PAGE);

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

    @Override
    public void loadItemsForCheck(RequestContent requestContent) throws ReceiverException {
        loadItemsWithStatus(requestContent, ItemStatus.CREATED);
    }

    @Override
    public void loadActiveItems(RequestContent requestContent) throws ReceiverException {
        loadItemsWithStatus(requestContent, ItemStatus.ACTIVE);
    }

    @Override
    public void loadComingItems(RequestContent requestContent) throws ReceiverException {
        loadItemsWithStatus(requestContent, ItemStatus.CONFIRMED);
    }

    @Override
    public void loadPurchasedItems(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);
        if (user != null) {

            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(itemDAO)) {
                FilterCriteria filterCriteria = new FilterCriteria();
                extractFilterParameters(requestContent, filterCriteria);
                OrderCriteria orderCriteria = extractOrderParameters(requestContent);

                if (!definePagesNumber(requestContent)) {
                    requestContent.setRequestAttribute(RequestConstant.PAGES,
                            (itemDAO.countRows(user.getId(), filterCriteria) / PageParameter.ITEMS_FOR_PAGE) + 1);
                }

                List<Item> items = itemDAO.findPurchasedItems(user.getId(), filterCriteria, orderCriteria,
                        defineOffset(requestContent, PageParameter.ITEMS_FOR_PAGE), PageParameter.ITEMS_FOR_PAGE);

                requestContent.setRequestAttribute(RequestConstant.ITEMS, items);
            } catch (DAOException e) {
                throw new ReceiverException(e);
            }
        }
    }

    @Override
    public void loadUserItems(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);
        if (user != null) {
            FilterCriteria filterCriteria = new FilterCriteria();
            try {
                filterCriteria.put(FilterQueryParameter.SELLER_ID, user.getId());
                loadItems(requestContent, filterCriteria);
            } catch (WrongFilterParameterException e) {
                throw new ReceiverException(e);
            }
        }
    }

    private boolean definePagesNumber(RequestContent requestContent) {
        String[] pages = requestContent.getRequestParameter(RequestConstant.PAGES);
        if (pages != null && !pages[0].isEmpty()) {
            requestContent.setRequestAttribute(RequestConstant.PAGES, pages[0]);
            return true;
        } else {
            return false;
        }
    }

    private int defineOffset(RequestContent requestContent, int unitsForPage) {
        String[] page = requestContent.getRequestParameter(RequestConstant.PAGE);

        int pageToGo;
        if (page != null) {
            pageToGo = Integer.valueOf(page[0]);
        } else {
            pageToGo = 1;
        }
        requestContent.setRequestAttribute(RequestConstant.PAGE, pageToGo);

        return (pageToGo - 1) * unitsForPage;
    }

    private void extractFilterParameters(RequestContent requestContent, FilterCriteria filterCriteria) {
        if (requestContent.getRequestParameter(RequestConstant.NOT_INITIAL) == null) {
            for (FilterQueryParameter filterQueryParameter : FilterQueryParameter.values()) {
                String[] requestParameter = requestContent.getRequestParameter(filterQueryParameter.name().toLowerCase().replaceAll("_", "-"));
                if (requestParameter != null && !requestParameter[0].isEmpty()) {
                    filterCriteria.put(filterQueryParameter, requestParameter[0]);
                }
            }
            requestContent.setSessionAttribute(RequestConstant.FILTER_CRITERIA, filterCriteria);
        } else {
            filterCriteria = (FilterCriteria) requestContent.getSessionAttribute(RequestConstant.FILTER_CRITERIA);
        }
    }

    private OrderCriteria extractOrderParameters(RequestContent requestContent) {
        OrderCriteria orderCriteria;

        if (requestContent.getRequestParameter(RequestConstant.NOT_INITIAL) == null) {
            String[] orderBy = requestContent.getRequestParameter(RequestConstant.ORDER_BY);
            String[] orderType = requestContent.getRequestParameter(RequestConstant.ORDER_TYPE);

            if (orderBy != null && orderType != null) {
                orderCriteria = new OrderCriteria(orderBy[0], orderType[0]);
            } else if (orderBy != null) {
                orderCriteria = new OrderCriteria(orderBy[0]);
            } else {
                orderCriteria = new OrderCriteria();
            }
            requestContent.setSessionAttribute(RequestConstant.ORDER_CRITERIA, orderCriteria);
        } else {
            orderCriteria = (OrderCriteria) requestContent.getSessionAttribute(RequestConstant.ORDER_CRITERIA);
        }

        return orderCriteria;
    }

    private void loadItems(RequestContent requestContent, FilterCriteria filterCriteria) throws ReceiverException {
        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {
            extractFilterParameters(requestContent, filterCriteria);
            OrderCriteria orderCriteria = extractOrderParameters(requestContent);

            if (!definePagesNumber(requestContent)) {
                requestContent.setRequestAttribute(RequestConstant.PAGES,
                        (itemDAO.countRows(filterCriteria) / PageParameter.ITEMS_FOR_PAGE) + 1);
            }

            List<Item> items = itemDAO.findItemsWithFilter(filterCriteria, orderCriteria,
                    defineOffset(requestContent, PageParameter.ITEMS_FOR_PAGE), PageParameter.ITEMS_FOR_PAGE);

            requestContent.setRequestAttribute(RequestConstant.ITEMS, items);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }

    }

    private void loadItemsWithStatus(RequestContent requestContent, ItemStatus itemStatus) throws ReceiverException {
        FilterCriteria filterCriteria = new FilterCriteria();
        try {
            filterCriteria.put(FilterQueryParameter.STATUS, itemStatus.ordinal());
            loadItems(requestContent, filterCriteria);
        } catch (WrongFilterParameterException e) {
            throw new ReceiverException(e);
        }
    }

}