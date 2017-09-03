package com.epam.auction.entity;

/**
 * Represents `photo` table from database.
 */
public class Photo extends Entity {

    /**
     * Name of the photo file.
     */
    private String fileName;
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
     * Constructs photo with file name and item id.
     *
     * @param fileName file name
     * @param itemId   item id
     */
    public Photo(String fileName, long itemId) {
        this.fileName = fileName;
        this.itemId = itemId;
    }

    /**
     * Constructs photo with id, file name and item id.
     *
     * @param id       id
     * @param fileName file name
     * @param itemId   item id
     */
    public Photo(int id, String fileName, int itemId) {
        super(id);
        this.fileName = fileName;
        this.itemId = itemId;
    }

    /**
     * Returns the name of the photo file.
     *
     * @return name of the photo file
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the name of the photo file.
     *
     * @param fileName name of the photo file
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
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
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        return itemId == photo.itemId &&
                (fileName != null ? fileName.equals(photo.fileName) : photo.fileName == null);
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + (int) (itemId ^ (itemId >>> 32));
        return result;
    }
}