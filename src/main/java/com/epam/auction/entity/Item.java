package com.epam.auction.entity;

import java.math.BigDecimal;
import java.sql.Date;

public class Item extends Entity {

    private String name;
    private String description;
    private BigDecimal startPrice;
    private BigDecimal blitzPrice;
    private BigDecimal actualPrice = new BigDecimal(0);
    private Date startDate;
    private Date closeDate;
    private ItemStatus status = ItemStatus.CREATED;
    private int itemCategoryId;
    private int sellerId;

    public Item() {
    }

    public Item(String name, String description, BigDecimal startPrice, BigDecimal blitzPrice, Date startDate, Date closeDate, int itemCategoryId, int sellerId) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.blitzPrice = blitzPrice;
        this.startDate = startDate;
        this.closeDate = closeDate;
        this.itemCategoryId = itemCategoryId;
        this.sellerId = sellerId;
    }

    public Item(int id, String name, String description, BigDecimal startPrice, BigDecimal blitzPrice, BigDecimal actualPrice, Date startDate, Date closeDate, ItemStatus status, int itemCategoryId, int sellerId) {
        super(id);
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.blitzPrice = blitzPrice;
        this.actualPrice = actualPrice;
        this.startDate = startDate;
        this.closeDate = closeDate;
        this.status = status;
        this.itemCategoryId = itemCategoryId;
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    public BigDecimal getBlitzPrice() {
        return blitzPrice;
    }

    public void setBlitzPrice(BigDecimal blitzPrice) {
        this.blitzPrice = blitzPrice;
    }

    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public ItemStatus getStatus() {
        return status;
    }

    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    public int getItemCategoryId() {
        return itemCategoryId;
    }

    public void setItemCategoryId(int itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    public int getSellerId() {
        return sellerId;
    }

    public void setSellerId(int sellerId) {
        this.sellerId = sellerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Item item = (Item) o;

        if (itemCategoryId != item.itemCategoryId) return false;
        if (sellerId != item.sellerId) return false;
        if (name != null ? !name.equals(item.name) : item.name != null) return false;
        if (description != null ? !description.equals(item.description) : item.description != null) return false;
        if (startPrice != null ? !startPrice.equals(item.startPrice) : item.startPrice != null) return false;
        if (blitzPrice != null ? !blitzPrice.equals(item.blitzPrice) : item.blitzPrice != null) return false;
        if (actualPrice != null ? !actualPrice.equals(item.actualPrice) : item.actualPrice != null) return false;
        if (startDate != null ? !startDate.equals(item.startDate) : item.startDate != null) return false;
        if (closeDate != null ? !closeDate.equals(item.closeDate) : item.closeDate != null) return false;
        return status == item.status;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startPrice != null ? startPrice.hashCode() : 0);
        result = 31 * result + (blitzPrice != null ? blitzPrice.hashCode() : 0);
        result = 31 * result + (actualPrice != null ? actualPrice.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (closeDate != null ? closeDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + itemCategoryId;
        result = 31 * result + sellerId;
        return result;
    }

}