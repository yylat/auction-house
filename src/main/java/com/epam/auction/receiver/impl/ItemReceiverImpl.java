package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.ItemCategoryDAO;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.PhotoDAO;
import com.epam.auction.dao.filter.FilterCriteria;
import com.epam.auction.dao.filter.FilterQueryParameter;
import com.epam.auction.dao.filter.OrderCriteria;
import com.epam.auction.dao.impl.ItemCategoryDAOImpl;
import com.epam.auction.dao.impl.ItemDAOImpl;
import com.epam.auction.dao.impl.PhotoDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.entity.Photo;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.exception.WrongFilterParameterException;
import com.epam.auction.receiver.ItemReceiver;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.util.Converter;
import com.epam.auction.validator.ItemValidator;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ItemReceiverImpl implements ItemReceiver {

    private final static int itemsForPage = 8;

    @Override
    public void loadCategories(RequestContent requestContent) throws ReceiverLayerException {
        ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemCategoryDAO)) {
            requestContent.setAjaxResponse(Converter.objectToJson(itemCategoryDAO.findAll()));
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e);
        }
    }

    @Override
    public void createItem(RequestContent requestContent) throws ReceiverLayerException {
        Item item = new Item(
                requestContent.getRequestParameter(RequestConstant.TITLE)[0],
                requestContent.getRequestParameter(RequestConstant.DESCRIPTION)[0],
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.START_PRICE)[0]),
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.BLITZ_PRICE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.START_DATE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.CLOSE_DATE)[0]),
                Integer.valueOf(requestContent.getRequestParameter(RequestConstant.CATEGORY_ID)[0]),
                ((User) requestContent.getSessionAttribute(RequestConstant.USER)).getId());

        ItemValidator itemValidator = new ItemValidator();
        if (itemValidator.validateItemParam(item)) {
            List<InputStream> files = requestContent.getFiles();

            ItemDAO itemDAO = new ItemDAOImpl();
            PhotoDAO photoDAO = new PhotoDAOImpl();

            DAOManager daoManager = new DAOManager(true, itemDAO, photoDAO);
            daoManager.beginTransaction();
            try {
                boolean itemCreated = itemDAO.create(item);
                boolean photosSaved = true;
                if (itemCreated && files != null) {
                    photosSaved = savePhotos(photoDAO, files, item.getId());
                }
                if (photosSaved) {
                    daoManager.commit();
                }
            } catch (DAOLayerException e) {
                daoManager.rollback();
                throw new ReceiverLayerException(e);
            } finally {
                daoManager.endTransaction();
            }
        } else {
            throw new ReceiverLayerException(itemValidator.getValidationMessage());
        }
    }

    @Override
    public void loadItemsForCheck(RequestContent requestContent) throws ReceiverLayerException {
        loadItemsWithStatus(requestContent, ItemStatus.CREATED);
    }

    @Override
    public void loadActiveItems(RequestContent requestContent) throws ReceiverLayerException {
        loadItemsWithStatus(requestContent, ItemStatus.ACTIVE);
    }

    @Override
    public void loadComingItems(RequestContent requestContent) throws ReceiverLayerException {
        loadItemsWithStatus(requestContent, ItemStatus.CONFIRMED);
    }

    @Override
    public void loadItem(RequestContent requestContent) throws ReceiverLayerException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {
            requestContent.setSessionAttribute(RequestConstant.ITEM,
                    itemDAO.findEntityById(itemId));
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e);
        }
    }

    @Override
    public void loadPurchasedItems(RequestContent requestContent) throws ReceiverLayerException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);
        if (user != null) {
            String[] pages = requestContent.getRequestParameter(RequestConstant.PAGES);

            int pageToGo;
            String[] page = requestContent.getRequestParameter(RequestConstant.PAGE);
            if (page != null) {
                pageToGo = Integer.valueOf(page[0]);
            } else {
                pageToGo = 1;
            }
            requestContent.setRequestAttribute(RequestConstant.PAGE, pageToGo);

            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(itemDAO)) {
                FilterCriteria filterCriteria = new FilterCriteria();
                extractFilterParameters(requestContent, filterCriteria);
                OrderCriteria orderCriteria = extractOrderParameters(requestContent);

                if (pages != null && pages[0] != null) {
                    requestContent.setRequestAttribute(RequestConstant.PAGES, pages[0]);
                } else {
                    requestContent.setRequestAttribute(RequestConstant.PAGES,
                            (itemDAO.countRows(user.getId(), filterCriteria) / itemsForPage) + 1);
                }

                List<Item> items = itemDAO.findPurchasedItems(user.getId(), filterCriteria, orderCriteria,
                        (pageToGo - 1) * itemsForPage, itemsForPage);

                requestContent.setRequestAttribute(RequestConstant.ITEMS, items);
            } catch (DAOLayerException e) {
                throw new ReceiverLayerException(e);
            }
        }
    }

    @Override
    public void updateItem(RequestContent requestContent) throws ReceiverLayerException {
        String newTitle = requestContent.getRequestParameter(RequestConstant.TITLE)[0];
        String newDescription = requestContent.getRequestParameter(RequestConstant.DESCRIPTION)[0];
        BigDecimal newStartPrice = new BigDecimal(requestContent.getRequestParameter(RequestConstant.START_PRICE)[0]);
        BigDecimal newBlitzPrice = new BigDecimal(requestContent.getRequestParameter(RequestConstant.BLITZ_PRICE)[0]);
        Date newStartDate = Date.valueOf(requestContent.getRequestParameter(RequestConstant.START_DATE)[0]);
        Date newCloseDate = Date.valueOf(requestContent.getRequestParameter(RequestConstant.CLOSE_DATE)[0]);

        ItemValidator itemValidator = new ItemValidator();

        if (itemValidator.validateItemParam(newTitle, newStartPrice, newBlitzPrice, newStartDate, newCloseDate)) {
            Item item = (Item) requestContent.getSessionAttribute(RequestConstant.ITEM);
            item.setName(newTitle);
            item.setDescription(newDescription);
            item.setStartPrice(newStartPrice);
            item.setBlitzPrice(newBlitzPrice);
            item.setStartDate(newStartDate);
            item.setCloseDate(newCloseDate);

            ItemDAO itemDAO = new ItemDAOImpl();
            DAOManager daoManager = new DAOManager(true, itemDAO);

            daoManager.beginTransaction();
            try {
                itemDAO.update(item);
                daoManager.commit();
            } catch (DAOLayerException | MethodNotSupportedException e) {
                daoManager.rollback();
                throw new ReceiverLayerException(e);
            } finally {
                daoManager.endTransaction();
            }


        }

    }

    @Override
    public void loadUserItems(RequestContent requestContent) throws ReceiverLayerException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);
        if (user != null) {
            FilterCriteria filterCriteria = new FilterCriteria();
            try {
                filterCriteria.put(FilterQueryParameter.SELLER_ID, user.getId());
                loadItems(requestContent, filterCriteria);
            } catch (WrongFilterParameterException e) {
                throw new ReceiverLayerException(e);
            }
        }
    }

    private void loadItemsWithStatus(RequestContent requestContent, ItemStatus itemStatus) throws ReceiverLayerException {
        FilterCriteria filterCriteria = new FilterCriteria();
        try {
            filterCriteria.put(FilterQueryParameter.STATUS, itemStatus.ordinal());
            loadItems(requestContent, filterCriteria);
        } catch (WrongFilterParameterException e) {
            throw new ReceiverLayerException(e);
        }
    }

    private void loadItems(RequestContent requestContent, FilterCriteria filterCriteria) throws ReceiverLayerException {
        String[] pages = requestContent.getRequestParameter(RequestConstant.PAGES);

        int pageToGo;
        String[] page = requestContent.getRequestParameter(RequestConstant.PAGE);
        if (page != null) {
            pageToGo = Integer.valueOf(page[0]);
        } else {
            pageToGo = 1;
        }
        requestContent.setRequestAttribute(RequestConstant.PAGE, pageToGo);

        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {
            extractFilterParameters(requestContent, filterCriteria);
            OrderCriteria orderCriteria = extractOrderParameters(requestContent);

            if (pages != null && pages[0] != null) {
                requestContent.setRequestAttribute(RequestConstant.PAGES, pages[0]);
            } else {
                requestContent.setRequestAttribute(RequestConstant.PAGES,
                        (itemDAO.countRows(filterCriteria) / itemsForPage) + 1);
            }

            List<Item> items = itemDAO.findItemsWithFilter(filterCriteria, orderCriteria,
                    (pageToGo - 1) * itemsForPage, itemsForPage);

            requestContent.setRequestAttribute(RequestConstant.ITEMS, items);
        } catch (DAOLayerException e) {
            throw new ReceiverLayerException(e);
        }

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

    private boolean savePhotos(PhotoDAO photoDAO, List<InputStream> files, int itemId) throws DAOLayerException {
        boolean result = false;
        List<Photo> photos = files.stream()
                .map(file -> new Photo(file, itemId))
                .collect(Collectors.toList());

        for (Photo photo : photos) {
            result = photoDAO.create(photo);
            if (!result) {
                return false;
            }
        }
        return result;
    }

}