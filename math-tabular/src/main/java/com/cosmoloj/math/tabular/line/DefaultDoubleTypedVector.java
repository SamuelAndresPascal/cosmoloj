package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.core.DoubleTypedTabulars;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleTypedVector extends AbstractTypedVector<Double> {

    public DefaultDoubleTypedVector(final Double... components) {
        super(components);
    }

    public DefaultDoubleTypedVector(final int nb) {
        super(nb);
    }

    @Override
    public Double linearForm(final TypedVector<Double> other) {
        return DoubleTypedTabulars.mult(this, other);
    }

    @Override
    public Class<Double> getField() {
        return Double.class;
    }
}
