package com.cosmoloj.util.sql;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Samuel Andrés
 */
public class AbstractIdentifiedDao {

    private final IdentifiedDaoFactory daoFactory;

    public AbstractIdentifiedDao(final IdentifiedDaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    protected IdentifiedDaoFactory getFactory() {
        return this.daoFactory;
    }

    protected static Double getDoubleOrNull(final ResultSet resultSet, final int index) throws SQLException {
        final double value = resultSet.getDouble(index);
        return resultSet.wasNull() ? null : value;
   }

    protected static Float getFloatOrNull(final ResultSet resultSet, final int index) throws SQLException {
        final float value = resultSet.getFloat(index);
        return resultSet.wasNull() ? null : value;
   }

    protected static Integer getIntegerOrNull(final ResultSet resultSet, final int index) throws SQLException {
        final int value = resultSet.getInt(index);
        return resultSet.wasNull() ? null : value;
   }

}
