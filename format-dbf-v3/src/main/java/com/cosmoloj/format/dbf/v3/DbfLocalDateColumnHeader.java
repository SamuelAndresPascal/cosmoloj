package com.cosmoloj.format.dbf.v3;

import java.nio.charset.StandardCharsets;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Samuel Andrés
 */
public final class DbfLocalDateColumnHeader extends DbfColumnHeader<LocalDate> {

    public DbfLocalDateColumnHeader(final String name, final int memory, final byte size, final byte decimals) {
        super(name, DbfType.DATE, memory, size, decimals);
    }

    @Override
    public LocalDate fromBytes(final byte[] bytes) {
        return LocalDate.parse(new String(bytes, StandardCharsets.ISO_8859_1),
                DateTimeFormatter.ofPattern(DbfUtil.FIELD_DATE_PATTERN));
    }

    @Override
    public byte[] toBytes(final LocalDate data) {
        final DecimalFormat decimalFormat = new DecimalFormat("00");
        final StringBuilder sb = new StringBuilder();
        sb.append(data.getYear());
        sb.append(decimalFormat.format(data.getMonthValue()));
        sb.append(decimalFormat.format(data.getDayOfMonth()));
        return sb.toString().getBytes(StandardCharsets.ISO_8859_1);
    }
}
