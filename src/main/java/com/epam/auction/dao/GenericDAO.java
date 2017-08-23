package com.epam.auction.dao;

import com.epam.auction.entity.Entity;
import com.epam.auction.exception.DAOLayerException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.db.ProxyConnection;

import java.util.List;

public interface GenericDAO<T extends Entity> {

    void setConnection(ProxyConnection connection);

    List<T> findAll() throws DAOLayerException;

    T findEntityById(int id) throws DAOLayerException;

    boolean delete(int id) throws DAOLayerException, MethodNotSupportedException;

    boolean create(T entity) throws DAOLayerException;

    boolean update(T entity) throws DAOLayerException, MethodNotSupportedException;

}