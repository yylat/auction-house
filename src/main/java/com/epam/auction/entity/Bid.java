package com.epam.auction.entity;

import java.math.BigDecimal;

public class Bid extends Entity {

    private int itemId;
    private int bidderId;
    private BigDecimal bidValue;
    private boolean isWinning;

    public Bid() {
    }

    public Bid(int id, int itemId, int bidderId, BigDecimal bidValue) {
        super(id);
        this.itemId = itemId;
        this.bidderId = bidderId;
        this.bidValue = bidValue;
        isWinning = true;
    }

    public Bid(int id, int itemId, int bidderId, BigDecimal bidValue, boolean isWinning) {
        super(id);
        this.itemId = itemId;
        this.bidderId = bidderId;
        this.bidValue = bidValue;
        this.isWinning = isWinning;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public int getBidderId() {
        return bidderId;
    }

    public void setBidderId(int bidderId) {
        this.bidderId = bidderId;
    }

    public BigDecimal getBidValue() {
        return bidValue;
    }

    public void setBidValue(BigDecimal bidValue) {
        this.bidValue = bidValue;
    }

    public boolean isWinning() {
        return isWinning;
    }

    public void setIsWinning(boolean isWinning) {
        this.isWinning = isWinning;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bid bid = (Bid) o;

        if (itemId != bid.itemId) return false;
        if (bidderId != bid.bidderId) return false;
        if (isWinning != bid.isWinning) return false;
        return bidValue != null ? bidValue.equals(bid.bidValue) : bid.bidValue == null;
    }

    @Override
    public int hashCode() {
        int result = itemId;
        result = 31 * result + bidderId;
        result = 31 * result + (bidValue != null ? bidValue.hashCode() : 0);
        result = 31 * result + (isWinning ? 1 : 0);
        return result;
    }
}