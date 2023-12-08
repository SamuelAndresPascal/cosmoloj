package com.cosmoloj.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.function.ToDoubleFunction;

/**
 *
 * @author Samuel Andrés
 */
public class DoubleParameterTabReader extends AbstractParameterTabReader<double[]> {

    public DoubleParameterTabReader(final InputStream is) {
        super(is, s -> new double[s]);
    }

    public DoubleParameterTabReader(final InputStream is, final String separatorRegexp) {
        super(is, s -> new double[s], separatorRegexp);
    }

    public DoubleParameterTabReader(final InputStream is, final String separatorRegexp, final String commentPrefix) {
        super(is, s -> new double[s], separatorRegexp, commentPrefix);
    }

    public void fill(final double[][] tab) throws IOException {
        fill(tab, Double::valueOf);
    }

    public void fill(final double[][] tab, final ToDoubleFunction<String> toDouble) throws IOException {

        fill(tab, () -> (final String[] t, final double[] u) -> {
            for (int j = 0, l = t.length; j < l; j++) {
                u[j] = toDouble.applyAsDouble(t[j]);
            }
        });
    }
}
