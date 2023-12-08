package com.cosmoloj.util;

import java.io.InputStream;
import java.util.function.ToIntFunction;

/**
 *
 * @author Samuel Andrés
 */
public class ShortParameterReader extends AbstractParameterReader<short[]> {

    private final ToIntFunction<String> toInt;

    public ShortParameterReader(final InputStream is) {
        super(is);
        this.toInt = Integer::valueOf;
    }

    public ShortParameterReader(final InputStream is, final ToIntFunction<String> toDouble) {
        super(is);
        this.toInt = toDouble;
    }

    public ShortParameterReader(final InputStream is, final ToIntFunction<String> toDouble,
            final String separatorRegexp) {
        super(is, separatorRegexp);
        this.toInt = toDouble;
    }

    public ShortParameterReader(final InputStream is, final ToIntFunction<String> toInt,
            final String separatorRegexp, final String commentPrefix) {
        super(is, separatorRegexp, commentPrefix);
        this.toInt = toInt;
    }

    @Override
    protected int handleLine(final String[] numbers, final short[] tab, final int cpt) {
        int n = cpt;
        for (final String number : numbers) {
            tab[n++] = (short) toInt.applyAsInt(number);
        }
        return n;
    }
}
