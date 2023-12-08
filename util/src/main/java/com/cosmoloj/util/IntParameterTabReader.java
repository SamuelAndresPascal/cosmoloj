package com.cosmoloj.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/**
 *
 * @author Samuel Andrés
 */
public class IntParameterTabReader extends AbstractParameterTabReader<int[]> {

    private static final IntFunction<int[]> TAB_SUPPLIER = s -> new int[s];

    public IntParameterTabReader(final InputStream is) {
        super(is, TAB_SUPPLIER);
    }

    public IntParameterTabReader(final InputStream is, final String separatorRegexp) {
        super(is, TAB_SUPPLIER, separatorRegexp);
    }

    public IntParameterTabReader(final InputStream is, final String separatorRegexp, final String commentPrefix) {
        super(is, TAB_SUPPLIER, separatorRegexp, commentPrefix);
    }

    public void fill(final int[][] tab) throws IOException {
        fill(tab, Integer::valueOf);
    }

    public void fill(final int[][] tab, final ToIntFunction<String> toInt) throws IOException {

        fill(tab, () -> (final String[] t, final int[] u) -> {
            for (int j = 0; j < t.length; j++) {
                u[j] = toInt.applyAsInt(t[j]);
            }
        });
    }
}
