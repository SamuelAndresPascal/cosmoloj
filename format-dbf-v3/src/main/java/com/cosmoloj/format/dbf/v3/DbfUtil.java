package com.cosmoloj.format.dbf.v3;

/**
 *
 * @author Samuel Andrés
 */
public final class DbfUtil {

    private DbfUtil() {
    };

    public static final short BASE_YEAR = 1900;

    public static final byte NOT_DELETED = 0x20;
    public static final byte DELETED = 0x2a;

    public static final String FIELD_DATE_PATTERN = "yyyyMMdd";
}
