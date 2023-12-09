package com.cosmoloj.math.tabular.line;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleTypedVectorFactory {

    public TypedVector<Double> get(final Double... components) {
        return new DefaultDoubleTypedVector(components);
    }

    public TypedVector<Double> get(final int dimension) {
        return new DefaultDoubleTypedVector(dimension);
    }
}
