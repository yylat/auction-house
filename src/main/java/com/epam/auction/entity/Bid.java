package com.epam.auction.entity;

import java.math.BigDecimal;

/**
 * Represents `bid` table from database.
 */
public class Bid extends Entity {

    /**
     * Represents the item id on which the bid is placed.
     */
    private long itemId;
    /**
     * Represents the bidder(user) id.
     */
    private long bidderId;
    /**
     * The value of the bid.
     */
    private BigDecimal bidValue;
    /**
     * Shows if bid is winning now or not.
     */
    private boolean isWinning;

    /**
     * Constructs bid without parameters.
     */
    public Bid() {
    }

    /**
     * Constructs bid with item id, bidder id and bid value.
     *
     * @param itemId   item id
     * @param bidderId bidder id
     * @param bidValue bid value
     */
    public Bid(long itemId, long bidderId, BigDecimal bidValue) {
        this.itemId = itemId;
        this.bidderId = bidderId;
        this.bidValue = bidValue;
    }

    /**
     * Constructs bid with id, item id, bidder id and bid value.
     *
     * @param id       id
     * @param itemId   item id
     * @param bidderId bidder id
     * @param bidValue bid value
     */
    public Bid(long id, long itemId, long bidderId, BigDecimal bidValue) {
        super(id);
        this.itemId = itemId;
        this.bidderId = bidderId;
        this.bidValue = bidValue;
        isWinning = true;
    }

    /**
     * Constructs bid with id, item id, bidder id, bid value and win status.
     *
     * @param id        id
     * @param itemId    item id
     * @param bidderId  bidder id
     * @param bidValue  bid value
     * @param isWinning win status
     */
    public Bid(long id, long itemId, long bidderId, BigDecimal bidValue, boolean isWinning) {
        super(id);
        this.itemId = itemId;
        this.bidderId = bidderId;
        this.bidValue = bidValue;
        this.isWinning = isWinning;
    }

    /**
     * Returns the item id.
     *
     * @return item id
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Sets the item id.
     *
     * @param itemId item id
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    /**
     * Returns the bidder id.
     *
     * @return bidder id
     */
    public long getBidderId() {
        return bidderId;
    }

    /**
     * Sets the bidder id.
     *
     * @param bidderId bidder id
     */
    public void setBidderId(long bidderId) {
        this.bidderId = bidderId;
    }

    /**
     * Returns the bid value.
     *
     * @return bid value
     */
    public BigDecimal getBidValue() {
        return bidValue;
    }

    /**
     * Sets the bid value.
     *
     * @param bidValue bid value
     */
    public void setBidValue(BigDecimal bidValue) {
        this.bidValue = bidValue;
    }

    /**
     * Returns <code>true</code> if bid is winning now.
     *
     * @return <code>true</code> if bid is winning now;
     * <code>false</code> otherwise
     */
    public boolean isWinning() {
        return isWinning;
    }

    /**
     * Sets whether bid is winning or not.
     *
     * @param isWinning win status
     */
    public void setIsWinning(boolean isWinning) {
        this.isWinning = isWinning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;

        return itemId == bid.itemId
                && bidderId == bid.bidderId
                && isWinning == bid.isWinning && (bidValue != null ? bidValue.equals(bid.bidValue) : bid.bidValue == null);
    }

    @Override
    public int hashCode() {
        int result = (int) (itemId ^ (itemId >>> 32));
        result = 31 * result + (int) (bidderId ^ (bidderId >>> 32));
        result = 31 * result + (bidValue != null ? bidValue.hashCode() : 0);
        result = 31 * result + (isWinning ? 1 : 0);
        return result;
    }
}