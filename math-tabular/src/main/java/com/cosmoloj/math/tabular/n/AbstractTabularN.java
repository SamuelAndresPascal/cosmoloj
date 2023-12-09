package com.cosmoloj.math.tabular.n;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public abstract class AbstractTabularN implements TabularN {

    private final Object tabular;
    private final int[] dimensions;
    private final boolean scalar;

    public AbstractTabularN(final Object tabular, final int... dimensions) {
        final Class<?> type = tabular.getClass();
        if (dimensions.length == 0) {
            if (dimensions.length != 0) {
                throw new IllegalArgumentException("dimensions should be null for a scalar value");
            }
            this.dimensions = new int[]{1};
            if (Double.class.equals(type)) {
                this.tabular = new double[]{(double) tabular};
            } else {
                throw new UnsupportedOperationException("type not supported for tabular N");
            }
            this.scalar = true;
        } else {
            this.dimensions = dimensions;
            this.tabular = copy(tabular, dimensions);
            this.scalar = false;
        }
    }

    protected Object getValue(final int... component) {
        if (scalar && component.length == 0) {
            return getValue(0);
        } else {
            Object internalArray = tabular;
            for (final int index : component) {
                internalArray = Array.get(internalArray, index);
            }
            return internalArray;
        }
    }

    @Override
    public int getOrder() {
        if (scalar) {
            return 0;
        } else {
            return dimensions.length;
        }
    }

    @Override
    public int getDimension(final int index) {
        return dimensions[index];
    }

    @Override
    public int[] getDimensions() {
        return Arrays.copyOf(dimensions, dimensions.length);
    }

    @Override
    public int getDimension() {
        int dim = 1;
        for (final int d : dimensions) {
            dim *= d;
        }
        return dim;
    }

    public abstract Object copy(Object tab, int... dim);
}
