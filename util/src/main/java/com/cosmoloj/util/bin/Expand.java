package com.cosmoloj.util.bin;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.nio.ByteOrder;

/**
 *
 * @author Samuel Andrés
 */
public final class Expand extends ExpandCore implements Closeable {

    private final ByteArrayOutputStream out;

    public Expand(final byte[] in, final int from, final int to, final FillOrder fOrder, final ByteOrder bOrder) {
        super(in, from, to, fOrder, bOrder);
        this.out = new ByteArrayOutputStream();
    }

    public Expand(final byte[] in, final FillOrder fOrder, final ByteOrder bOrder) {
        this(in, 0, in.length, fOrder, bOrder);
    }

    public void expand(final int size) {
        out.write(get(size));
    }

    public byte[] getByteArray() {
        return out.toByteArray();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
