package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.core.DoubleTabulars;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleVector implements DoubleVector {

    private final double[] matrix;

    public DefaultDoubleVector(final double... matrix) {
        this.matrix = DoubleTabulars.copy(matrix);
    }

    @Override
    public double[] getMatrix() {
        return DoubleTabulars.copy(matrix);
    }

    @Override
    public int getDimension() {
        return matrix.length;
    }

    @Override
    public int[] getDimensions() {
        return new int[]{matrix.length};
    }

    @Override
    public double get(final int... i) {
        if (i.length != VECTOR_ORDER) {
            throw new IllegalArgumentException();
        }
        return matrix[i[0]];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder().append('(');
        for (int i = 0; i < matrix.length; i++) {
            sb.append(matrix[i]);
            if (i != matrix.length - 1) {
                sb.append(',').append(' ');
            }
        }
        return sb.append(')').toString();
    }
}
