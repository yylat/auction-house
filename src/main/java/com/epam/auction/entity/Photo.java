package com.epam.auction.entity;

import java.io.InputStream;

public class Photo extends Entity {

    private InputStream photoFile;
    private int itemId;

    public Photo() {
    }

    public Photo(InputStream photoFile, int itemId) {
        this.photoFile = photoFile;
        this.itemId = itemId;
    }

    public Photo(int id, InputStream photoFile, int itemId) {
        super(id);
        this.photoFile = photoFile;
        this.itemId = itemId;
    }

    public InputStream getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(InputStream photoFile) {
        this.photoFile = photoFile;
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

        if (id != photo.id) return false;
        if (itemId != photo.itemId) return false;
        return photoFile != null ? photoFile.equals(photo.photoFile) : photo.photoFile == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (photoFile != null ? photoFile.hashCode() : 0);
        result = 31 * result + itemId;
        return result;
    }
}
