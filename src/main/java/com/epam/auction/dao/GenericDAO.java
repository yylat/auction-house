package com.epam.auction.dao;

import com.epam.auction.entity.Entity;
import com.epam.auction.exception.DAOException;
import com.epam.auction.exception.MethodNotSupportedException;
import com.epam.auction.db.ProxyConnection;

import java.util.List;

public interface GenericDAO<T extends Entity> {

    void setConnection(ProxyConnection connection);

    List<T> findAll() throws DAOException;

    T findEntityById(int id) throws DAOException;

    boolean delete(int id) throws DAOException, MethodNotSupportedException;

    boolean create(T entity) throws DAOException;

    boolean update(T entity) throws DAOException, MethodNotSupportedException;

}