package com.cosmoloj.util;

import java.io.InputStream;
import java.util.function.ToDoubleFunction;

/**
 *
 * @author Samuel Andrés
 */
public class DoubleParameterReader extends AbstractParameterReader<double[]> {

    private final ToDoubleFunction<String> toDouble;

    public DoubleParameterReader(final InputStream is) {
        super(is);
        this.toDouble = Double::valueOf;
    }

    public DoubleParameterReader(final InputStream is, final ToDoubleFunction<String> toDouble) {
        super(is);
        this.toDouble = toDouble;
    }

    public DoubleParameterReader(final InputStream is, final ToDoubleFunction<String> toDouble,
            final String separatorRegexp) {
        super(is, separatorRegexp);
        this.toDouble = toDouble;
    }

    public DoubleParameterReader(final InputStream is, final ToDoubleFunction<String> toDouble,
            final String separatorRegexp, final String commentPrefix) {
        super(is, separatorRegexp, commentPrefix);
        this.toDouble = toDouble;
    }

    @Override
    protected int handleLine(final String[] numbers, final double[] tab, final int firstIdx) {
        int cpt = firstIdx;
        for (final String number : numbers) {
            tab[cpt] = toDouble.applyAsDouble(number);
            cpt++;
        }
        return cpt;
    }
}
