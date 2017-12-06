package com.epam.auction.entity;

import java.math.BigDecimal;
import java.sql.Date;

/**
 * Represents `item` table from database.
 */
public class Item extends Entity {

    /**
     * Name of the item.
     */
    private String name;
    /**
     * Description of the item.
     */
    private String description;
    /**
     * Start price that represents the min price at which the auction begins.
     */
    private BigDecimal startPrice;
    /**
     * Blitz price.
     * If bidder make bid with such value, auction ends.
     */
    private BigDecimal blitzPrice;
    /**
     * Actual price of the item now.
     * Default value - 0.
     */
    private BigDecimal actualPrice = BigDecimal.ZERO;
    /**
     * Auction start date.
     */
    private Date startDate;
    /**
     * Auction close date.
     */
    private Date closeDate;
    /**
     * Represents status of the item.
     * Default value - 'CREATED'
     */
    private ItemStatus status = ItemStatus.CREATED;
    /**
     * Represents item category id.
     */
    private long itemCategoryId;
    /**
     * Represents seller(user) id.
     */
    private long sellerId;

    private DeliveryStatus deliveryStatus = DeliveryStatus.NO_DELIVERY;

    /**
     * Constructs item without parameters.
     */
    public Item() {
    }

    /**
     * Constructs item with name, description, start price, blitz price,
     * start date and close date.
     *
     * @param name        name
     * @param description description
     * @param startPrice  start price
     * @param blitzPrice  blitz price
     * @param startDate   start date
     * @param closeDate   close date
     */
    public Item(String name, String description, BigDecimal startPrice, BigDecimal blitzPrice, Date startDate, Date closeDate) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.blitzPrice = blitzPrice;
        this.startDate = startDate;
        this.closeDate = closeDate;
    }

    /**
     * Constructs item with name, description, start price, blitz price,
     * start date, close date, item category id and seller id.
     *
     * @param name           name
     * @param description    description
     * @param startPrice     start price
     * @param blitzPrice     blitz price
     * @param startDate      start date
     * @param closeDate      close date
     * @param itemCategoryId item category id
     * @param sellerId       seller id
     */
    public Item(String name, String description, BigDecimal startPrice, BigDecimal blitzPrice, Date startDate, Date closeDate, long itemCategoryId, long sellerId) {
        this.name = name;
        this.description = description;
        this.startPrice = startPrice;
        this.blitzPrice = blitzPrice;
        this.startDate = startDate;
        this.closeDate = closeDate;
        this.itemCategoryId = itemCategoryId;
        this.sellerId = sellerId;
    }

    /**
     * Constructs item with id, name, description, start price,
     * blitz price, actual price, start date, close date,
     * status, item category id and seller id.
     *
     * @param id             id
     * @param name           name
     * @param description    description
     * @param startPrice     start price
     * @param blitzPrice     blitz price
     * @param actualPrice    actual price
     * @param startDate      start date
     * @param closeDate      close date
     * @param status         status
     * @param itemCategoryId item category id
     * @param sellerId       seller id
     */
    public Item(long id, String name, String description,
                BigDecimal startPrice, BigDecimal blitzPrice, BigDecimal actualPrice,
                Date startDate, Date closeDate, ItemStatus status,
                long itemCategoryId, long sellerId, DeliveryStatus deliveryStatus) {
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
        this.deliveryStatus = deliveryStatus;
    }

    /**
     * Returns the name of the item.
     *
     * @return name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the item.
     *
     * @param name name of the item
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns the description of the item.
     *
     * @return description of the item
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item.
     *
     * @param description description of the item
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the start price of the item.
     *
     * @return start price of the item
     */
    public BigDecimal getStartPrice() {
        return startPrice;
    }

    /**
     * Sets the start price of the item.
     *
     * @param startPrice start price of the item
     */
    public void setStartPrice(BigDecimal startPrice) {
        this.startPrice = startPrice;
    }

    /**
     * Returns the blitz price of the item.
     *
     * @return blitz price of the item
     */
    public BigDecimal getBlitzPrice() {
        return blitzPrice;
    }

    /**
     * Sets the blitz price of the item.
     *
     * @param blitzPrice blitz price of the item
     */
    public void setBlitzPrice(BigDecimal blitzPrice) {
        this.blitzPrice = blitzPrice;
    }

    /**
     * Returns the actual price of the item.
     *
     * @return actual price of the item
     */
    public BigDecimal getActualPrice() {
        return actualPrice;
    }

    /**
     * Sets the actual price of the item.
     *
     * @param actualPrice actual price of the item
     */
    public void setActualPrice(BigDecimal actualPrice) {
        this.actualPrice = actualPrice;
    }

    /**
     * Returns the start date of the auction.
     *
     * @return start date of the auction
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * Sets the start date of the auction.
     *
     * @param startDate start date of the auction
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * Returns the close date of the auction.
     *
     * @return close date of the auction
     */
    public Date getCloseDate() {
        return closeDate;
    }

    /**
     * Sets the close date of the auction.
     *
     * @param closeDate close date of the auction
     */
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    /**
     * Returns the status of the item.
     *
     * @return status of the item
     */
    public ItemStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the auction.
     *
     * @param status status of the auction
     */
    public void setStatus(ItemStatus status) {
        this.status = status;
    }

    /**
     * Returns the item category id.
     *
     * @return item category id
     */
    public long getItemCategoryId() {
        return itemCategoryId;
    }

    /**
     * Sets the item category id.
     *
     * @param itemCategoryId item category id
     */
    public void setItemCategoryId(long itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }

    /**
     * Returns the item seller id.
     *
     * @return item seller id
     */
    public long getSellerId() {
        return sellerId;
    }

    /**
     * Sets the item seller id.
     *
     * @param sellerId item seller id
     */
    public void setSellerId(long sellerId) {
        this.sellerId = sellerId;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Item item = (Item) o;

        return itemCategoryId == item.itemCategoryId
                && sellerId == item.sellerId
                && (name != null ? name.equals(item.name) : item.name == null)
                && (description != null ? description.equals(item.description) : item.description == null)
                && (startPrice != null ? startPrice.equals(item.startPrice) : item.startPrice == null)
                && (blitzPrice != null ? blitzPrice.equals(item.blitzPrice) : item.blitzPrice == null)
                && (actualPrice != null ? actualPrice.equals(item.actualPrice) : item.actualPrice == null)
                && (startDate != null ? startDate.equals(item.startDate) : item.startDate == null)
                && (closeDate != null ? closeDate.equals(item.closeDate) : item.closeDate == null)
                && status == item.status && deliveryStatus == item.deliveryStatus;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (startPrice != null ? startPrice.hashCode() : 0);
        result = 31 * result + (blitzPrice != null ? blitzPrice.hashCode() : 0);
        result = 31 * result + (actualPrice != null ? actualPrice.hashCode() : 0);
        result = 31 * result + (startDate != null ? startDate.hashCode() : 0);
        result = 31 * result + (closeDate != null ? closeDate.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (int) (itemCategoryId ^ (itemCategoryId >>> 32));
        result = 31 * result + (int) (sellerId ^ (sellerId >>> 32));
        result = 31 * result + (deliveryStatus != null ? deliveryStatus.hashCode() : 0);
        return result;
    }

}