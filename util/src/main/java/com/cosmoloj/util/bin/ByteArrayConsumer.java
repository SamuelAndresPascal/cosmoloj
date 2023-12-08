package com.cosmoloj.util.bin;

import java.util.function.Supplier;

/**
 *
 * @author Samuel Andrés
 */
class ByteArrayConsumer implements BinaryArrayConsumer, Supplier<byte[]> {

    private byte[] result = null;
    private int cpt = 0;

    @Override
    public void accept(final int value) {
        result[cpt++] = (byte) value;
    }

    @Override
    public byte[] get() {
        return result;
    }

    @Override
    public void size(final int size) {
        result = new byte[size];
    }
}
