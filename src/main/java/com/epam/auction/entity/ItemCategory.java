package com.epam.auction.entity;

/**
 * Represents `item_category` table from database.
 */
public class ItemCategory extends Entity {

    /**
     * Description of the item category (e.g., book, jewel).
     */
    private String description;
    /**
     * Represents the parent category for this category.
     */
    private long parentItemCategoryId;

    /**
     * Constructs item category without parameters.
     */
    public ItemCategory() {
    }

    /**
     * Constructs item category with id, description and parent category id.
     *
     * @param id                   id
     * @param description          description
     * @param parentItemCategoryId parent category id
     */
    public ItemCategory(long id, String description, long parentItemCategoryId) {
        super(id);
        this.description = description;
        this.parentItemCategoryId = parentItemCategoryId;
    }

    /**
     * Returns the description of the item category.
     *
     * @return description of the item category
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the item category.
     *
     * @param description description of the item category
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the parent category id.
     *
     * @return parent category id
     */
    public long getParentItemCategoryId() {
        return parentItemCategoryId;
    }

    /**
     * Sets the parent category id.
     *
     * @param parentItemCategoryId parent category id
     */
    public void setParentItemCategoryId(long parentItemCategoryId) {
        this.parentItemCategoryId = parentItemCategoryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ItemCategory that = (ItemCategory) o;

        return parentItemCategoryId == that.parentItemCategoryId &&
                (description != null ? description.equals(that.description) : that.description == null);
    }

    @Override
    public int hashCode() {
        int result = description != null ? description.hashCode() : 0;
        result = 31 * result + (int) (parentItemCategoryId ^ (parentItemCategoryId >>> 32));
        return result;
    }

}