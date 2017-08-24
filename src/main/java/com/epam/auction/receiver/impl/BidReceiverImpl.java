package com.epam.auction.receiver.impl;

import com.epam.auction.command.RequestContent;
import com.epam.auction.dao.BidDAO;
import com.epam.auction.dao.ItemDAO;
import com.epam.auction.dao.impl.BidDAOImpl;
import com.epam.auction.dao.impl.ItemDAOImpl;
import com.epam.auction.db.DAOManager;
import com.epam.auction.entity.Bid;
import com.epam.auction.entity.Item;
import com.epam.auction.entity.User;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.ReceiverLayerException;
import com.epam.auction.receiver.BidReceiver;
import com.epam.auction.receiver.RequestConstant;
import com.epam.auction.util.MessageProvider;

import java.math.BigDecimal;
import java.util.*;

public class BidReceiverImpl implements BidReceiver {

    private final static int bidsForPage = 20;

    @Override
    public void makeBid(RequestContent requestContent) throws ReceiverLayerException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);
        if (user != null) {
            int itemId = Integer.valueOf(requestContent.getRequestParameter(RequestConstant.ITEM_ID)[0]);
            BigDecimal bidValue = new BigDecimal(requestContent.getRequestParameter(RequestConstant.BID_VALUE)[0]);

            BidDAO bidDAO = new BidDAOImpl();
            DAOManager daoManager = new DAOManager(true, bidDAO);

            daoManager.beginTransaction();
            try {
                MessageProvider messageProvider = new MessageProvider((Locale) requestContent.getSessionAttribute(RequestConstant.LOCALE));

                if (bidDAO.findWinning(itemId).getBidderId() != user.getId()) {
                    if (bidValue.compareTo(user.getBalance()) <= 0) {
                        Bid bid = new Bid(itemId, user.getId(), bidValue);
                        bidDAO.create(bid);
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
            } catch (DAOLayerException e) {
                daoManager.rollback();
                throw new ReceiverLayerException(e.getMessage(), e);
            } finally {
                daoManager.endTransaction();
            }
        }
    }

    @Override
    public void loadBids(RequestContent requestContent) throws ReceiverLayerException {
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
            BidDAO bidDAO = new BidDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(bidDAO, itemDAO)) {
                if (pages == null) {
                    requestContent.setRequestAttribute(RequestConstant.PAGES,
                            (bidDAO.countRows(user.getId()) / bidsForPage) + 1);
                }

                List<Bid> bids = bidDAO.findUsersBids(user.getId(), (pageToGo - 1) * bidsForPage, bidsForPage);

                Map<Bid, Item> bidItemMap = new LinkedHashMap<>();
                for (Bid bid : bids) {
                    bidItemMap.put(bid, itemDAO.findEntityById(bid.getItemId()));
                }

                requestContent.setRequestAttribute(RequestConstant.BID_ITEM_MAP, bidItemMap);
            } catch (DAOLayerException e) {
                throw new ReceiverLayerException(e.getMessage(), e);
            }
        }
    }

}