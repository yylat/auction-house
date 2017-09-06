package com.epam.auction.entity;

import java.util.Arrays;
import java.util.Date;

/**
 * Represents `notification` table from database.
 */
public class Notification extends Entity {

    /**
     * Type of the notification.
     */
    private NotificationType type;
    /**
     * Represent recipient id(user).
     */
    private long userId;
    /**
     * Represents subject id(item).
     */
    private long itemId;
    /**
     * Date and time of the notification.
     */
    private Date dateTime;

    /**
     * Constructs notification without parameters.
     */
    public Notification() {
    }

    /**
     * Constructs notification with id, type, user id, item id, date and time.
     *
     * @param id       id
     * @param type     type
     * @param userId   user id
     * @param itemId   item id
     * @param dateTime date and time
     */
    public Notification(long id, NotificationType type, long userId, long itemId, Date dateTime) {
        super(id);
        this.type = type;
        this.userId = userId;
        this.itemId = itemId;
        this.dateTime = dateTime;
    }

    /**
     * Returns the type of the notification.
     *
     * @return type of the notification
     */
    public NotificationType getType() {
        return type;
    }

    /**
     * Sets the type of the notification.
     *
     * @param type type of the notification
     */
    public void setType(NotificationType type) {
        this.type = type;
    }

    /**
     * Returns the user id of the notification.
     *
     * @return user id
     */
    public long getUserId() {
        return userId;
    }

    /**
     * Sets the user id of the notification.
     *
     * @param userId user id
     */
    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Returns item id of the notification.
     *
     * @return item id
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Sets item id of the notification.
     *
     * @param itemId item id
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns date and time of the notification.
     *
     * @return date and time of the notification
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Sets date and time of the notification.
     *
     * @param dateTime date and time of the notification
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Notification that = (Notification) o;

        return userId == that.userId
                && itemId == that.itemId
                && type == that.type
                && (dateTime != null ? dateTime.equals(that.dateTime) : that.dateTime == null);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (dateTime != null ? dateTime.hashCode() : 0);
        return result;
    }

    /**
     * Represents `notification_type` table in database
     */
    public enum NotificationType {

        /**
         * Item was approved.
         */
        ITEM_CONFIRMED,
        /**
         * Item was discard.
         */
        ITEM_NOT_CONFIRMED,
        /**
         * Item was sold.
         */
        ITEM_SOLD,
        /**
         * Auction time is over.
         * No bids was made.
         */
        NO_BIDS_FOR_ITEM,
        /**
         * Seller canceled auction.
         */
        SELLER_CANCELED_AUCTION,
        /**
         * Bid won.
         * Item sold.
         */
        BID_WON,
        /**
         * Bid was beaten.
         */
        BID_BEATEN;

        /**
         * Returns notification type with id (ordinal number in enum).
         *
         * @param id id of the notification type
         * @return notification type
         */
        public static NotificationType define(int id) {
            return Arrays.stream(NotificationType.values())
                    .filter(notificationType -> id == notificationType.ordinal())
                    .findFirst()
                    .orElse(null);
        }

    }

}