package com.epam.auction.dao.functional;

import com.epam.auction.entity.Entity;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents an operation that accepts two input arguments and returns
 * no result. One argument is type of the {@link Entity}, another -
 * {@link PreparedStatement}.
 *
 * @param <T> the type of the {@link Entity}
 */
@FunctionalInterface
public interface StatementBiConsumer<T extends Entity> {

    /**
     * Performs this operation on given {@link Entity} and
     * {@link PreparedStatement} arguments.
     *
     * @param entity    {@link Entity} argument
     * @param statement {@link PreparedStatement} argument
     * @throws SQLException if can not perform an operation
     */
    void accept(T entity, PreparedStatement statement) throws SQLException;

}