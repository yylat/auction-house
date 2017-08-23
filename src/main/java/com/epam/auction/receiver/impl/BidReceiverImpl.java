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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BidReceiverImpl implements BidReceiver {

    private final static int bidsForPage = 20;

    @Override
    public void loadBids(RequestContent requestContent) throws ReceiverLayerException {
        User user = (User) requestContent.getSessionAttribute(RequestConstant.USER);

        if (user != null) {
            BidDAO bidDAO = new BidDAOImpl();
            ItemDAO itemDAO = new ItemDAOImpl();

            try (DAOManager daoManager = new DAOManager(bidDAO, itemDAO)) {
                List<Bid> bids;

                String[] lastBidIdOb = requestContent.getRequestParameter(RequestConstant.LAST_ITEM_ID);
                String[] firstBidOb = requestContent.getRequestParameter(RequestConstant.FIRST_ITEM_ID);

                if (lastBidIdOb != null) {
                    bids = bidDAO.findNextUserBids(user.getId(), Integer.valueOf(lastBidIdOb[0]), bidsForPage + 1);
                    requestContent.setRequestAttribute(RequestConstant.FIRST_ITEM_ID, bids.get(0).getId());
                    requestContent.setRequestAttribute(RequestConstant.LAST_ITEM_ID,
                            hasMore(requestContent, bids));
                } else if (firstBidOb != null) {
                    bids = bidDAO.findPrevUserBids(user.getId(), Integer.valueOf(firstBidOb[0]), bidsForPage + 1);
                    requestContent.setRequestAttribute(RequestConstant.FIRST_ITEM_ID,
                            hasMore(requestContent, bids));
                    requestContent.setRequestAttribute(RequestConstant.HAS_NEXT, true);
                } else {
                    bids = bidDAO.findUserBids(user.getId(), bidsForPage + 1);
                    requestContent.setRequestAttribute(RequestConstant.FIRST_ITEM_ID, null);
                    requestContent.setRequestAttribute(RequestConstant.LAST_ITEM_ID,
                            hasMore(requestContent, bids));
                }

                Map<Bid, Item> bidItemMap = new LinkedHashMap<>();

                for (Bid bid : bids) {
                    bidItemMap.put(bid,
                            itemDAO.findEntityById(bid.getItemId()));
                }

                requestContent.setRequestAttribute(RequestConstant.BID_ITEM_MAP, bidItemMap);
            } catch (DAOLayerException e) {
                throw new ReceiverLayerException(e.getMessage(), e);
            }
        }
    }


    private Integer hasMore(RequestContent requestContent, List<Bid> bids) {
        if ((bidsForPage + 1) == bids.size()) {
            bids = bids.subList(0, bidsForPage);
            return bids.get(bidsForPage + 1).getId();
        } else {
            return null;
        }
    }

}