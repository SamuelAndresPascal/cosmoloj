package com.cosmoloj.format.dbf.v3;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Samuel Andrés
 */
public final class DbfNumericDoubleColumnHeader extends DbfColumnHeader<Double> {

    public DbfNumericDoubleColumnHeader(final String name, final int memory, final byte size, final byte decimal) {
        super(name, DbfType.NUMERIC, memory, size, decimal);
    }

    @Override
    public Double fromBytes(final byte[] bytes) {
        return Double.parseDouble(new String(bytes, StandardCharsets.ISO_8859_1).trim());
    }

    @Override
    public byte[] toBytes(final Double data) {
        return new DecimalFormat(buildPattern(getDecimals()),
                new DecimalFormatSymbols(Locale.US)).format(data).getBytes(StandardCharsets.ISO_8859_1);
    }

    private static String buildPattern(final int decimal) {
        if (decimal == 0) {
            return "#";
        } else {
            final StringBuilder sb = new StringBuilder();
            sb.append('#').append('.');
            for (int i = 0; i < decimal; i++) {
                sb.append('#');
            }
            return sb.toString();
        }
    }
}
