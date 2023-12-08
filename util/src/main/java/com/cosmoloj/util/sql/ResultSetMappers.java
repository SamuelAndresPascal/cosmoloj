package com.cosmoloj.util.sql;

/**
 *
 * @author Samuel Andrés
 */
public final class ResultSetMappers {

    private ResultSetMappers() {
    }

    public static final ResultSetMapper<String> SINGLE_STRING = rs -> rs.getString(1);
    public static final ResultSetMapper<Short> SINGLE_SHORT = rs -> rs.getShort(1);
    public static final ResultSetMapper<Integer> SINGLE_INT = rs -> rs.getInt(1);
    public static final ResultSetMapper<Long> SINGLE_LONG = rs -> rs.getLong(1);
    public static final ResultSetMapper<Float> SINGLE_FLOAT = rs -> rs.getFloat(1);
    public static final ResultSetMapper<Double> SINGLE_DOUBLE = rs -> rs.getDouble(1);
    public static final ResultSetMapper<Boolean> SINGLE_BOOLEAN = rs -> rs.getBoolean(1);
}
