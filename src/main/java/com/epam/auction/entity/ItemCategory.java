package com.epam.auction.entity;

public class ItemCategory extends Entity {

    private String description;
    private int parentItemCategoryId;

    public ItemCategory() {
    }

    public ItemCategory(int id, String description, int parentItemCategoryId) {
        super(id);
        this.description = description;
        this.parentItemCategoryId = parentItemCategoryId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getParentItemCategoryId() {
        return parentItemCategoryId;
    }

    public void setParentItemCategoryId(int parentItemCategoryId) {
        this.parentItemCategoryId = parentItemCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCategory that = (ItemCategory) o;

        if (id != that.id) return false;
        if (parentItemCategoryId != that.parentItemCategoryId) return false;
        return description != null ? description.equals(that.description) : that.description == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + parentItemCategoryId;
        return result;
    }

}