package com.cosmoloj.math.tabular.line;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 *
 * @author Samuel Andrés
 * @param <K> <span class="fr">type de valeurs du vecteur</span>
 */
public abstract class AbstractTypedVector<K> implements TypedVector<K> {

    private final K[] matrix;

    public AbstractTypedVector(final K... components) {
        this.matrix = newMatrix(components.length);
        System.arraycopy(components, 0, matrix, 0, matrix.length);
    }

    public AbstractTypedVector(final int nb) {
        this.matrix = newMatrix(nb);
    }

    protected final K[] newMatrix(final int nb) {
        return (K[]) Array.newInstance(getField(), nb);
    }

    @Override
    public K[] getMatrix() {
        return matrix;
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
    public K get(final int... i) {
        if (i.length != VECTOR_ORDER) {
            throw new IllegalArgumentException();
        }
        return matrix[i[0]];
    }

    @Override
    public void set(final K c, final int... i) {
        if (i.length != VECTOR_ORDER) {
            throw new IllegalArgumentException();
        }
        matrix[i[0]] = c;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder().append('(');
        for (int i = 0; i < matrix.length; i++) {
            sb.append(matrix[i].toString());
            if (i != matrix.length - 1) {
                sb.append(',').append(' ');
            }
        }
        return sb.append(')').toString();
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
        return Arrays.equals(matrix, ((AbstractTypedVector<K>) obj).matrix);
    }
}
