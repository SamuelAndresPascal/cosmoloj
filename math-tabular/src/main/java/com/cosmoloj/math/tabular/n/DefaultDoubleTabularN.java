package com.cosmoloj.math.tabular.n;

import java.lang.reflect.Array;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleTabularN extends AbstractTabularN implements DoubleTabularN {

    public DefaultDoubleTabularN(final Object tabular, final int... dimensions) {
        super(tabular, dimensions);
    }

    @Override
    public double get(final int... component) {
        return (double) getValue(component);
    }

    @Override
    public Object copy(final Object original, final int... dim) {
        if (original instanceof Object[]) {
            final Object[] o = (Object[]) original;
            final Object r = Array.newInstance(double.class, dim);
            for (int i = 0; i < o.length; i++) {
                final int subLength = dim.length - 1;
                final int[] dim1 = new int[subLength];
                System.arraycopy(dim, 1, dim1, 0, subLength);
                Array.set(r, i, copy(o[i], dim1));
            }
            return r;
        } else {
            final double[] o = (double[]) original;
            final double[] r = new double[o.length];
            System.arraycopy(o, 0, r, 0, o.length);
            return r;
        }
    }
}
