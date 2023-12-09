package com.cosmoloj.math.tabular.line;

import com.cosmoloj.math.tabular.core.BooleanTabulars;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultBooleanVector implements BooleanVector {

    private boolean[] matrix;

    public DefaultBooleanVector(final boolean... components) {
        this.matrix = new boolean[components.length];
        System.arraycopy(components, 0, matrix, 0, matrix.length);
    }

    public DefaultBooleanVector(final int... components) {
        final boolean[] bComponents = new boolean[components.length];
        for (int i = 0; i < bComponents.length; i++) {
            final int c = components[i];
            if (c != 0 && c != 1) {
                throw new IllegalArgumentException();
            }
            bComponents[i] = (components[i] == 1);
        }
        this.matrix = new boolean[bComponents.length];
        System.arraycopy(bComponents, 0, matrix, 0, matrix.length);
    }

    public DefaultBooleanVector(final int dimension) {
        this.matrix = new boolean[dimension];
    }

    public DefaultBooleanVector() {
    }

    @Override
    public boolean[] getMatrix() {
        return matrix;
    }

    @Override
    public void setMatrix(final boolean... components) {
        System.arraycopy(components, 0, matrix, 0, matrix.length);
    }

    @Override
    public void resetMatrix(final boolean... m) {
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
    public void set(final boolean c, final int... i) {
        if (i.length != VECTOR_ORDER) {
            throw new IllegalArgumentException();
        }
        matrix[i[0]] = c;
    }

    @Override
    public boolean get(final int... i) {
        if (i.length != VECTOR_ORDER) {
            throw new IllegalArgumentException();
        }
        return matrix[i[0]];
    }

    @Override
    public boolean linearForm(final BooleanVector other) {
        return BooleanTabulars.mult(this, other);
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
        final DefaultBooleanVector other = (DefaultBooleanVector) obj;
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
