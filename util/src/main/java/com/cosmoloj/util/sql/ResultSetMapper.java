package com.cosmoloj.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Samuel Andrés
 * @param <R>
 */
@FunctionalInterface
public interface ResultSetMapper<R> {

    R map(ResultSet resultSet) throws SQLException;
}
