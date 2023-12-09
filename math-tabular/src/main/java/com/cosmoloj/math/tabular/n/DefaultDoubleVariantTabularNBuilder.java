package com.cosmoloj.math.tabular.n;

import java.lang.reflect.Array;
import java.util.Iterator;

/**
 *
 * @author Samuel Andrés
 */
final class DefaultDoubleVariantTabularNBuilder extends NamedIndexManager implements Iterable<int[]> {

    private final Object tabular;
    private final int[] dimensions;
    private final boolean scalar;

    DefaultDoubleVariantTabularNBuilder(final Class<?> type, final VariantIndex... vindices) {
        super(vindices);
        if (vindices.length == 0) {
            this.dimensions = new int[]{1};
            if (double.class.equals(type)) {
                this.tabular = new double[1];
            } else {
                throw new UnsupportedOperationException("type not supported for tabular N");
            }
            this.scalar = true;
        } else {
            this.dimensions = DefaultDoubleIndicedTabularN.indicesToDimensions(vindices);
            this.tabular = Array.newInstance(type, this.dimensions);
            this.scalar = false;
        }
    }

    public void setValue(final double value, final int... component) {
        if (scalar && component.length == 0) {
            setValue(value, 0);
        } else {
            Object internalArray = tabular;
            for (int i = 0; i < component.length - 1; i++) {
                internalArray = Array.get(internalArray, component[i]);
            }
            Array.set(internalArray, component[component.length - 1], value);
        }
    }

    public Object getTabular() {
        if (scalar) {
            return ((double[]) tabular)[0];
        }
        return tabular;
    }

    public boolean isScalar() {
        return scalar;
    }

    @Override
    public Iterator<int[]> iterator() {
        return new TabularIterator(this.dimensions);
    }

}
