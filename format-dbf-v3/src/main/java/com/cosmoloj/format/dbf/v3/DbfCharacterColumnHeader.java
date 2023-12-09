package com.cosmoloj.format.dbf.v3;

import java.nio.charset.Charset;

/**
 *
 * @author Samuel Andrés
 */
public final class DbfCharacterColumnHeader extends DbfColumnHeader<String> {

    private final boolean trimValues;
    private final Charset charset;

    public DbfCharacterColumnHeader(final String name, final int memory, final byte size, final byte decimals,
            final Charset charset, final boolean trimValues) {
        super(name, DbfType.CHARACTER, memory, size, decimals);
        this.charset = charset;
        this.trimValues = trimValues;
    }

    @Override
    public String fromBytes(final byte[] bytes) {
        final String raw = new String(bytes, charset);
        return trimValues ? raw.trim() : raw;
    }

    @Override
    public byte[] toBytes(final String data) {
        return data.getBytes(charset);
    }
}
