package com.epam.auction.entity;

public class Photo extends Entity {

    private String fileName;
    private int itemId;

    public Photo() {
    }

    public Photo(String fileName, int itemId) {
        this.fileName = fileName;
        this.itemId = itemId;
    }

    public Photo(int id, String fileName, int itemId) {
        super(id);
        this.fileName = fileName;
        this.itemId = itemId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Photo photo = (Photo) o;

        if (itemId != photo.itemId) return false;
        return fileName != null ? fileName.equals(photo.fileName) : photo.fileName == null;
    }

    @Override
    public int hashCode() {
        int result = fileName != null ? fileName.hashCode() : 0;
        result = 31 * result + itemId;
        return result;
    }

}