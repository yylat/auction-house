package com.epam.auction.dao.functional;

import java.sql.PreparedStatement;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementConsumer {

    void accept(PreparedStatement statement) throws SQLException;

}