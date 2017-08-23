package com.epam.auction.dao.functional;

import com.epam.auction.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementBiConsumer<T extends Entity> {

    void accept(T entity, PreparedStatement statement) throws SQLException;

}