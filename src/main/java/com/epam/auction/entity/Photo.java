package com.epam.auction.entity;

/**
 * Represents `photo` table from database.
 */
public class Photo extends Entity {

    /**
     * Item id.
     * Shows for which item photo belongs.
     */
    private long itemId;

    /**
     * Constructs photo without parameters.
     */
    public Photo() {
    }

    /**
     * Constructs photo with id, file name and item id.
     *
     * @param id     id
     * @param itemId item id
     */
    public Photo(long id, long itemId) {
        super(id);
        this.itemId = itemId;
    }

    /**
     * Returns the item id of the photo.
     *
     * @return item id of the photo
     */
    public long getItemId() {
        return itemId;
    }

    /**
     * Sets the item id of the photo.
     *
     * @param itemId item id of the photo
     */
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Photo photo = (Photo) o;

        return itemId == photo.itemId;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        return result;
    }

}