package com.epam.auction.receiver.impl;

import com.epam.auction.receiver.AdminReceiver;
import com.epam.auction.receiver.BidReceiver;
import com.epam.auction.receiver.CommonReceiver;
import com.epam.auction.receiver.ItemCategoryReceiver;
import com.epam.auction.receiver.ItemReceiver;
import com.epam.auction.receiver.NotificationReceiver;
import com.epam.auction.receiver.PhotoReceiver;
import com.epam.auction.receiver.UserReceiver;

public class ReceiverFactory {

    private final AdminReceiver adminReceiver = new AdminReceiverImpl();
    private final BidReceiver bidReceiver = new BidReceiverImpl();
    private final CommonReceiver commonReceiver = new CommonReceiverImpl();
    private final ItemCategoryReceiver itemCategoryReceiver = new ItemCategoryReceiverImpl();
    private final ItemReceiver itemReceiver = new ItemReceiverImpl();
    private final NotificationReceiver notificationReceiver = new NotificationReceiverImpl();
    private final PhotoReceiver photoReceiver = new PhotoReceiverImpl();
    private final UserReceiver userReceiver = new UserReceiverImpl();

    private static class Holder {
        private static final ReceiverFactory INSTANCE = new ReceiverFactory();
    }

    private ReceiverFactory() {
    }

    public static ReceiverFactory getInstance() {
        return Holder.INSTANCE;
    }

    public AdminReceiver getAdminReceiver() {
        return adminReceiver;
    }

    public BidReceiver getBidReceiver() {
        return bidReceiver;
    }

    public CommonReceiver getCommonReceiver() {
        return commonReceiver;
    }

    public ItemCategoryReceiver getItemCategoryReceiver() {
        return itemCategoryReceiver;
    }

    public ItemReceiver getItemReceiver() {
        return itemReceiver;
    }

    public NotificationReceiver getNotificationReceiver() {
        return notificationReceiver;
    }

    public PhotoReceiver getPhotoReceiver() {
        return photoReceiver;
    }

    public UserReceiver getUserReceiver() {
        return userReceiver;
    }
}