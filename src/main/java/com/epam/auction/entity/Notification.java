package com.epam.auction.entity;

public class Notification extends Entity {

    private String description;
    private int userId;
    private int itemId;

    public Notification() {
    }

    public Notification(int id, String description, int userId, int itemId) {
        super(id);
        this.description = description;
        this.userId = userId;
        this.itemId = itemId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Notification that = (Notification) o;

        if (id != that.id) return false;
        if (userId != that.userId) return false;
        if (itemId != that.itemId) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + userId;
        result = 31 * result + itemId;
        return result;
    }
}
