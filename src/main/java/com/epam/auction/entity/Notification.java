package com.epam.auction.entity;

import java.util.Arrays;
import java.util.Date;

public class Notification extends Entity {

    private NotificationType type;
    private int userId;
    private int itemId;
    private Date dateTime;

    public Notification() {
    }

    public Notification(int id, NotificationType type, int userId, int itemId, Date dateTime) {
        super(id);
        this.type = type;
        this.userId = userId;
        this.itemId = itemId;
        this.dateTime = dateTime;
    }

    public NotificationType getType() {
        return type;
    }

    public void setType(NotificationType type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (userId != that.userId) return false;
        if (itemId != that.itemId) return false;
        if (type != that.type) return false;
        return dateTime != null ? dateTime.equals(that.dateTime) : that.dateTime == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + userId;
        result = 31 * result + itemId;
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    public enum NotificationType {

        ITEM_CONFIRMED,
        ITEM_NOT_CONFIRMED,
        ITEM_SOLD,
        NO_BIDS_FOR_ITEM,
        SELLER_CANCELED_AUCTION,
        BID_WIN,
        BID_BEATEN;

        public static NotificationType define(int id) {
            return Arrays.stream(NotificationType.values())
                    .filter(notificationType -> id == notificationType.ordinal())
                    .findFirst()
                    .orElse(null);
        }

    }

}