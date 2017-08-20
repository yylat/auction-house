package com.epam.auction.receiver.impl;

import com.epam.auction.constant.RequestConstant;
import com.epam.auction.content.RequestContent;
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class BidReceiverImpl implements BidReceiver {
    @Override
    public void loadBids(RequestContent requestContent) throws ReceiverLayerException {
        int userId = ((User) requestContent.getSessionAttribute(RequestConstant.USER)).getId();

        BidDAO bidDAO = new BidDAOImpl();
        ItemDAO itemDAO = new ItemDAOImpl();

        try (DAOManager daoManager = new DAOManager(bidDAO, itemDAO)) {
            List<Bid> bids = bidDAO.findAll(userId);
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