package com.epam.auction.entity;

import java.io.Serializable;

/**
 * Represents common database table's properties.
 */
public abstract class Entity implements Serializable {

    /**
     * Represent primary key from table in database.
     */
    protected long id;

    /**
     * Constructs entity without parameters.
     */
    public Entity() {
    }

    /**
     * Constructs entity with the id key.
     *
     * @param id id
     */
    public Entity(long id) {
        this.id = id;
    }

    /**
     * Returns the id of entity.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id of entity.
     *
     * @param id id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Returns the number of fields for specific entity class.
     * Count all declared fields and add 1 (for id).
     *
     * @return fields number
     */
    public int getFieldsNumber() {
        return getClass().getDeclaredFields().length + 1;
    }

}