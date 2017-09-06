package com.epam.auction.receiver.impl;

import com.epam.auction.controller.RequestContent;
import com.epam.auction.dao.BidDAO;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.impl.BidDAOImpl;
import com.epam.auction.dao.impl.ItemDAOImpl;
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
import java.util.stream.Collectors;

public class BidReceiverImpl implements BidReceiver {

    @Override
    public void makeBid(RequestContent requestContent) throws ReceiverException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);
        if (user != null) {
            int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);
            BigDecimal bidValue = new BigDecimal(requestContent.getRequestParameter(RequestConstant.BID_VALUE)[0]);

            BidDAO bidDAO = new BidDAOImpl();
            DAOManager daoManager = new DAOManager(true, bidDAO);

            daoManager.beginTransaction();
            try {
                MessageProvider messageProvider = new MessageProvider((Locale) requestContent.getSessionAttribute(RequestConstant.LOCALE));

                Bid winningBid = bidDAO.findWinning(itemId);
                if (winningBid == null || winningBid.getBidderId() != user.getId()) {
                    if (bidValue.compareTo(user.getBalance()) <= 0) {
                        Bid bid = new Bid(itemId, user.getId(), bidValue);
                        bidDAO.create(bid);
                        user.setBalance(user.getBalance().subtract(bidValue));
                        updateSessionItem(requestContent, bidValue);
                        requestContent.setSessionAttribute(RequestConstant.MESSAGE,
                                messageProvider.getMessage(MessageProvider.BID_MADE_SUCCESSFULLY));
                    } else {
                        requestContent.setSessionAttribute(RequestConstant.MESSAGE,
                                messageProvider.getMessage(MessageProvider.NOT_ENOUGH_MONEY));
                    }
                } else {
                    requestContent.setSessionAttribute(RequestConstant.MESSAGE,
                            messageProvider.getMessage(MessageProvider.YOUR_BID_IS_WINNING));
                }
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
            BidDAO bidDAO = new BidDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(bidDAO, itemDAO)) {

                PaginationHelper paginationHelper = new PaginationHelper(SiteManager.getInstance().getBidsForPage());
                paginationHelper.definePage(requestContent);
                if (!paginationHelper.pagesNumberDefined(requestContent)) {
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

    private void updateSessionItem(RequestContent requestContent, BigDecimal bidValue) {
        Item item = (Item) requestContent.getSessionAttribute(RequestConstant.ITEM);
        item.setActualPrice(bidValue);
        if (bidValue.compareTo(item.getBlitzPrice()) >= 0) {
            item.setStatus(ItemStatus.SOLD);
        }
    }

}