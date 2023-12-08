package com.cosmoloj.util;

import java.io.InputStream;
import java.util.function.ToIntFunction;

/**
 *
 * @author Samuel Andrés
 */
public class IntParameterReader extends AbstractParameterReader<int[]> {

    private final ToIntFunction<String> toInt;

    public IntParameterReader(final InputStream is) {
        super(is);
        this.toInt = Integer::valueOf;
    }

    public IntParameterReader(final InputStream is, final ToIntFunction<String> toInt) {
        super(is);
        this.toInt = toInt;
    }

    public IntParameterReader(final InputStream is, final ToIntFunction<String> toInt, final String separatorRegexp) {
        super(is, separatorRegexp);
        this.toInt = toInt;
    }

    public IntParameterReader(final InputStream is, final ToIntFunction<String> toInt,
            final String separatorRegexp, final String commentPrefix) {
        super(is, separatorRegexp, commentPrefix);
        this.toInt = toInt;
    }

    @Override
    protected int handleLine(final String[] numbers, final int[] tab, final int cpt) {
        int n = cpt;
        for (final String number : numbers) {
            tab[n++] = toInt.applyAsInt(number);
        }
        return n;
    }
}
