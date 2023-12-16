package com.cosmoloj.format.csv;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 *
 * @author Samuel Andrés
 */
public final class CsvUtil {

    private CsvUtil() {
    }

    public static final Charset DEFAULT_CHARSET = StandardCharsets.US_ASCII;
    public static final int DEFAULT_VALUE_SEPARATOR = ',';
    public static final int DEFAULT_VALUE_LEFT_LIMIT = '"';
    public static final int DEFAULT_VALUE_RIGHT_LIMIT = '"';
    public static final int DEFAULT_ESCAPE = '"';
}
