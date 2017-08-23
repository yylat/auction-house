package com.epam.auction.dao.functional;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface StatementFunction<T> {

    T apply(ResultSet resultSet) throws SQLException;

}