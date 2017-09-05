package com.epam.auction.receiver.impl;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.dao.ItemCategoryDAO;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.PhotoDAO;
import com.epam.auction.dao.criteria.FilterCriteria;
import com.epam.auction.dao.criteria.FilterQueryParameter;
import com.epam.auction.dao.criteria.OrderCriteria;
import com.epam.auction.dao.impl.ItemCategoryDAOImpl;
import com.epam.auction.dao.impl.ItemDAOImpl;
import com.epam.auction.dao.impl.PhotoDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.ItemStatus;
import com.epam.auction.entity.Photo;
import com.epam.auction.entity.User;
import com.epam.auction.exception.*;
import com.epam.auction.receiver.ItemReceiver;
import com.epam.auction.receiver.PaginationHelper;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.receiver.SiteManager;
import com.epam.auction.util.JSONConverter;
import com.epam.auction.util.DateFixer;
import com.epam.auction.util.PhotoLoader;
import com.epam.auction.validator.ItemValidator;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;

public class ItemReceiverImpl implements ItemReceiver {

    @Override
    public void loadCategories(RequestContent requestContent) throws ReceiverException {
        ItemCategoryDAO itemCategoryDAO = new ItemCategoryDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemCategoryDAO)) {
            requestContent.setAjaxResponse(JSONConverter.objectAsJson(itemCategoryDAO.findAll()));
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void createItem(RequestContent requestContent) throws ReceiverException {
        Item item = new Item(
                requestContent.getRequestParameter(RequestConstant.TITLE)[0],
                requestContent.getRequestParameter(RequestConstant.DESCRIPTION)[0],
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.START_PRICE)[0]),
                new BigDecimal(requestContent.getRequestParameter(RequestConstant.BLITZ_PRICE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.START_DATE)[0]),
                Date.valueOf(requestContent.getRequestParameter(RequestConstant.CLOSE_DATE)[0]),
                Integer.valueOf(requestContent.getRequestParameter(RequestConstant.CATEGORY)[0]),
                ((User) requestContent.getSessionAttribute(RequestConstant.USER)).getId());

        ItemValidator itemValidator = new ItemValidator();
        if (itemValidator.validateItemParam(item)) {
            List<InputStream> files = requestContent.getFiles();

            ItemDAO itemDAO = new ItemDAOImpl();
            PhotoDAO photoDAO = new PhotoDAOImpl();

            DAOManager daoManager = new DAOManager(true, itemDAO, photoDAO);
            daoManager.beginTransaction();
            try {
                itemDAO.create(item);

                if (files != null) {
                    savePhotos(photoDAO, files, item.getId());
                }

                daoManager.commit();
            } catch (DAOException | MethodNotSupportedException | PhotoLoadingException e) {
                daoManager.rollback();
                throw new ReceiverException(e);
            } finally {
                daoManager.endTransaction();
            }
        } else {
            throw new ReceiverException(itemValidator.getValidationMessage());
        }
    }

    @Override
    public void loadItem(RequestContent requestContent) throws ReceiverException {
        int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);

        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {
            requestContent.setSessionAttribute(RequestConstant.ITEM,
                    itemDAO.findEntityById(itemId));
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    @Override
    public void updateItem(RequestContent requestContent) throws ReceiverException {
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
                updateItemForCheck(item, itemDAO);
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
    public void addPhotos(RequestContent requestContent) throws ReceiverException {
        Item item = (Item) requestContent.getSessionAttribute(RequestConstant.ITEM);

        List<InputStream> files = requestContent.getFiles();
        if (files != null) {

            PhotoDAO photoDAO = new PhotoDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();
            DAOManager daoManager = new DAOManager(true, photoDAO, itemDAO);

            daoManager.beginTransaction();
            try {
                savePhotos(photoDAO, files, item.getId());
                updateItemForCheck(item, itemDAO);
                daoManager.commit();
            } catch (DAOException | MethodNotSupportedException | PhotoLoadingException e) {
                daoManager.rollback();
                throw new ReceiverException(e);
            } finally {
                daoManager.endTransaction();
            }
        }
    }

    @Override
    public void deleteItem(RequestContent requestContent) throws ReceiverException {
        long itemId = ((Item) requestContent.getSessionAttribute(RequestConstant.ITEM)).getId();

        ItemDAO itemDAO = new ItemDAOImpl();
        PhotoDAO photoDAO = new PhotoDAOImpl();
        DAOManager daoManager = new DAOManager(true, itemDAO, photoDAO);

        daoManager.beginTransaction();
        try {
            photoDAO.deleteItemPhotos(itemId);
            itemDAO.delete(itemId);
            daoManager.commit();
        } catch (DAOException | MethodNotSupportedException e) {
            daoManager.rollback();
            throw new ReceiverException(e);
        } finally {
            daoManager.endTransaction();
        }
    }

    @Override
    public void cancelAuction(RequestContent requestContent) throws ReceiverException {
        updateItemStatus(requestContent, ItemStatus.CANCELED);
    }

    @Override
    public void approveItem(RequestContent requestContent) throws ReceiverException {
        updateItemStatus(requestContent, ItemStatus.CONFIRMED);
    }

    @Override
    public void discardItem(RequestContent requestContent) throws ReceiverException {
        updateItemStatus(requestContent, ItemStatus.NOT_CONFIRMED);
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

                PaginationHelper paginationHelper = new PaginationHelper(SiteManager.getInstance().getItemsForPage());
                paginationHelper.definePage(requestContent);
                if (!paginationHelper.pagesNumberDefined(requestContent)) {
                    paginationHelper.definePages(requestContent, itemDAO.countRows(user.getId(), filterCriteria));
                }

                List<Item> items = itemDAO.findPurchasedItems(user.getId(), filterCriteria, orderCriteria,
                        paginationHelper.findOffset(), paginationHelper.getLimit());

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

    @Override
    public void searchItems(RequestContent requestContent) throws ReceiverException {
        FilterCriteria filterCriteria = (FilterCriteria) requestContent.getSessionAttribute(RequestConstant.FILTER_CRITERIA);
        OrderCriteria orderCriteria = (OrderCriteria) requestContent.getSessionAttribute(RequestConstant.ORDER_CRITERIA);
        filterCriteria.put(FilterQueryParameter.SEARCH_NAME,
                requestContent.getRequestParameter(RequestConstant.SEARCH_NAME)[0]);
        loadItems(requestContent, filterCriteria, orderCriteria);
    }

    private void extractFilterParameters(RequestContent requestContent, FilterCriteria filterCriteria) {
        if (requestContent.getRequestParameter(RequestConstant.INITIAL) == null) {
            for (FilterQueryParameter filterQueryParameter : FilterQueryParameter.values()) {
                String[] requestParameter = requestContent
                        .getRequestParameter(filterQueryParameter.name().toLowerCase().replaceAll("_", "-"));
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

        if (requestContent.getRequestParameter(RequestConstant.INITIAL) == null) {
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

    private void loadItemsWithStatus(RequestContent requestContent, ItemStatus itemStatus) throws ReceiverException {
        FilterCriteria filterCriteria = new FilterCriteria();
        try {
            filterCriteria.put(FilterQueryParameter.STATUS, itemStatus.ordinal());
            loadItems(requestContent, filterCriteria);
        } catch (WrongFilterParameterException e) {
            throw new ReceiverException(e);
        }
    }

    private void loadItems(RequestContent requestContent, FilterCriteria filterCriteria) throws ReceiverException {
        extractFilterParameters(requestContent, filterCriteria);
        OrderCriteria orderCriteria = extractOrderParameters(requestContent);
        loadItems(requestContent, filterCriteria, orderCriteria);
    }

    private void loadItems(RequestContent requestContent, FilterCriteria filterCriteria, OrderCriteria orderCriteria)
            throws ReceiverException {
        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(itemDAO)) {

            PaginationHelper paginationHelper = new PaginationHelper(SiteManager.getInstance().getItemsForPage());
            paginationHelper.definePage(requestContent);
            if (!paginationHelper.pagesNumberDefined(requestContent)) {
                paginationHelper.definePages(requestContent, itemDAO.countRows(filterCriteria));
            }

            List<Item> items = itemDAO.findItemsWithFilter(filterCriteria, orderCriteria,
                    paginationHelper.findOffset(), paginationHelper.getLimit());

            requestContent.setRequestAttribute(RequestConstant.ITEMS, items);
        } catch (DAOException e) {
            throw new ReceiverException(e);
        }
    }

    private void updateItemStatus(RequestContent requestContent, ItemStatus itemStatus) throws ReceiverException {
        Item item = (Item) requestContent.getSessionAttribute(RequestConstant.ITEM);

        ItemDAO itemDAO = new ItemDAOImpl();

        DAOManager daoManager = new DAOManager(true, itemDAO);
        daoManager.beginTransaction();

        try {
            itemDAO.updateItemStatus(item.getId(), itemStatus);
            item.setStatus(itemStatus);
            daoManager.commit();
        } catch (DAOException e) {
            daoManager.rollback();
            throw new ReceiverException(e);
        } finally {
            daoManager.endTransaction();
        }
    }

    private void updateItemForCheck(Item item, ItemDAO itemDAO) throws MethodNotSupportedException, DAOException {
        ItemValidator itemValidator = new ItemValidator();
        if (!itemValidator.validateStartDate(item.getStartDate())) {
            item.setStartDate(DateFixer.addDays(2));
        }
        if (!itemValidator.validateCloseDate(item.getCloseDate(), item.getStartDate())) {
            item.setCloseDate(DateFixer.addDays(item.getStartDate(), 2));
        }
        item.setStatus(ItemStatus.CREATED);
        itemDAO.update(item);
    }

    private void savePhotos(PhotoDAO photoDAO, List<InputStream> files, long itemId)
            throws DAOException, MethodNotSupportedException, PhotoLoadingException {
        PhotoLoader photoLoader = new PhotoLoader();
        for (int i = 0; i < files.size(); i++) {
            photoDAO.create(new Photo(photoLoader.savePhotoToServer(files.get(i), i), itemId));
        }
    }

}