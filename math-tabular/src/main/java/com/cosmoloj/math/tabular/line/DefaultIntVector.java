package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.core.IntTabulars;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultIntVector implements IntVector {

    private int[] matrix;

    public DefaultIntVector(final int... components) {
        this.matrix = new int[components.length];
        System.arraycopy(components, 0, matrix, 0, matrix.length);
    }

    public DefaultIntVector(final int dimension) {
        this.matrix = new int[dimension];
    }

    public DefaultIntVector() {
    }

    @Override
    public int[] getMatrix() {
        return matrix;
    }

    @Override
    public void setMatrix(final int... components) {
        System.arraycopy(components, 0, matrix, 0, matrix.length);
    }

    @Override
    public void resetMatrix(final int... m) {
        this.matrix = m;
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
    public void set(final int c, final int... i) {
        if (i.length != VECTOR_ORDER) {
            throw new IllegalArgumentException();
        }
        matrix[i[0]] = c;
    }

    @Override
    public int get(final int... i) {
        if (i.length != VECTOR_ORDER) {
            throw new IllegalArgumentException();
        }
        return matrix[i[0]];
    }

    @Override
    public int linearForm(final IntVector other) {
        return IntTabulars.mult(this, other);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + Arrays.hashCode(this.matrix);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DefaultIntVector other = (DefaultIntVector) obj;
        if (!Arrays.equals(this.matrix, other.matrix)) {
            return false;
        }
        return true;
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
