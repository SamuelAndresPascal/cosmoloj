package com.cosmoloj.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Samuel Andrés
 */
public final class SqlUtil {

    private SqlUtil() {
    }

    public static Double doubleOrNull(final ResultSet resultSet, final int column) throws SQLException {
        final double value = resultSet.getDouble(column);
        return resultSet.wasNull() ? null : value;
    }

    public static Float floatOrNull(final ResultSet resultSet, final int column) throws SQLException {
        final float value = resultSet.getFloat(column);
        return resultSet.wasNull() ? null : value;
    }

    public static Integer intOrNull(final ResultSet resultSet, final int column) throws SQLException {
        final int value = resultSet.getInt(column);
        return resultSet.wasNull() ? null : value;
    }

    public static Long longOrNull(final ResultSet resultSet, final int column) throws SQLException {
        final long value = resultSet.getLong(column);
        return resultSet.wasNull() ? null : value;
    }
}
