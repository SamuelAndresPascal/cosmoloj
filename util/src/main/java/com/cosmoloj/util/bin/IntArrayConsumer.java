package com.cosmoloj.util.bin;

import java.util.function.Supplier;

/**
 *
 * @author Samuel Andrés
 */
class IntArrayConsumer implements BinaryArrayConsumer, Supplier<int[]> {

    private int[] result = null;
    private int cpt = 0;

    @Override
    public void accept(final int value) {
        result[cpt++] = value;
    }

    @Override
    public int[] get() {
        return result;
    }

    @Override
    public void size(final int size) {
        result = new int[size];
    }

}
