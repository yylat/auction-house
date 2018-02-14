package com.epam.auction.receiver.impl;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.dao.BidDAO;
import com.epam.auction.dao.impl.DAOFactory;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.*;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.exception.ReceiverException;
import com.epam.auction.receiver.BidReceiver;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.util.SiteManager;
import com.epam.auction.util.MessageProvider;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

class BidReceiverImpl implements BidReceiver {

    @Override
    public void makeBid(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);
        if (user != null) {
            Item item = (Item) requestContent.getSessionAttribute(RequestConstant.ITEM);
            BigDecimal bidValue = new BigDecimal(requestContent.getRequestParameter(RequestConstant.BID_VALUE)[0]);

            BidDAO bidDAO = DAOFactory.getInstance().getBidDAO();
            DAOManager daoManager = new DAOManager(true, bidDAO);

            daoManager.beginTransaction();
            try {
                MessageProvider messageProvider =
                        new MessageProvider((Locale) requestContent.getSessionAttribute(RequestConstant.LOCALE));

                String resultMessage = makeBid(bidDAO, item, user, bidValue, messageProvider);

                requestContent.setSessionAttribute(RequestConstant.MESSAGE, resultMessage);
                requestContent.setSessionAttribute(RequestConstant.WAS_SHOWN, false);

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
    public void loadBids(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            BidDAO bidDAO = DAOFactory.getInstance().getBidDAO();
            ItemDAO itemDAO = DAOFactory.getInstance().getItemDAO();

            try (DAOManager daoManager = new DAOManager(bidDAO, itemDAO)) {

                PaginationHelper paginationHelper = new PaginationHelper(SiteManager.getInstance().getBidsForPage());
                paginationHelper.definePage(requestContent);
                if (paginationHelper.pagesNumberNotDefined(requestContent)) {
                    paginationHelper.definePages(requestContent, bidDAO.countRows(user.getId()));
                }

                List<Bid> bids = bidDAO.findUsersBids(user.getId(),
                        paginationHelper.findOffset(), paginationHelper.getLimit());

                Set<Long> itemsIds = bids.stream().map(Bid::getItemId).collect(Collectors.toSet());
                Map<Long, Item> items = new HashMap<>();
                for (long itemId : itemsIds) {
                    items.put(itemId, itemDAO.findEntityById(itemId));
                }

                requestContent.setRequestAttribute(RequestConstant.BIDS, bids);
                requestContent.setRequestAttribute(RequestConstant.ITEMS, items);
            } catch (DAOException e) {
                throw new ReceiverException(e);
            }
        }
    }

    private String makeBid(BidDAO bidDAO, Item item, User user, BigDecimal bidValue,
                           MessageProvider messageProvider) throws MethodNotSupportedException, DAOException {
        Lock lock = new ReentrantLock();
        lock.lock();
        String resultMessage;
        try {
                Bid winningBid = bidDAO.findWinning(item.getId());

                if (winningBid == null) {
                    bidDAO.create(new Bid(item.getId(), user.getId(), bidValue));

                    resultMessage = messageProvider.getMessage(MessageProvider.BID_MADE_SUCCESSFULLY);
                    updateSessionItem(item, bidValue);

                } else if (winningBid.getBidderId() == user.getId()) {
                    resultMessage = messageProvider.getMessage(MessageProvider.YOUR_BID_IS_WINNING);

                } else if (winningBid.getBidValue().compareTo(bidValue) <= 0) {
                    bidDAO.create(new Bid(item.getId(), user.getId(), bidValue), winningBid.getId());

                    resultMessage = messageProvider.getMessage(MessageProvider.BID_MADE_SUCCESSFULLY);
                    updateSessionItem(item, bidValue);

                } else {
                    resultMessage = messageProvider.getMessage(MessageProvider.WINNING_BID_CHANGED);
                    updateSessionItem(item, winningBid.getBidValue());
                }
        } finally {
            lock.unlock();
        }
        return resultMessage;
    }

    private void updateSessionItem(Item item, BigDecimal bidValue) {
        item.setActualPrice(bidValue);
        if (bidValue.compareTo(item.getBlitzPrice()) >= 0) {
            item.setStatus(ItemStatus.SOLD);
        }
    }

}