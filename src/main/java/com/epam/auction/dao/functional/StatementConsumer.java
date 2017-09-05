package com.epam.auction.dao.functional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Represents an operation that accepts a single {@link PreparedStatement}
 * argument and returns no result.
 */
@FunctionalInterface
public interface StatementConsumer {

    /**
     * Performs this operation on given {@link PreparedStatement}.
     *
     * @param statement {@link PreparedStatement} argument
     * @throws SQLException if can not perform an operation
     */
    void accept(PreparedStatement statement) throws SQLException;

}