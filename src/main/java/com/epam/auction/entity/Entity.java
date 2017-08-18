package com.epam.auction.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable {

    protected int id;

    public Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFieldsNumber(){
        return getClass().getDeclaredFields().length + 1;
    }

}