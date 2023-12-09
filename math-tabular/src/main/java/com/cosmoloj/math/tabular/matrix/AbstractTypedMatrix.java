package com.cosmoloj.math.tabular.matrix;

import java.lang.reflect.Array;

/**
 *
 * @author Samuel Andrés
 * @param <K> <span class="fr">type de valeurs de la matrice</span>
 */
public abstract class AbstractTypedMatrix<K> implements TypedMatrix<K> {

    private final K[][] matrix;

    public AbstractTypedMatrix(final K[][] matrix) {
        this.matrix = matrix;
    }

    public AbstractTypedMatrix(final Dimension dim, final int dimLength, final K... components) {
        if (components.length % dimLength != 0) {
            throw new IllegalArgumentException();
        }

        if (dim == Dimension.COLUMN) {
            final int rowNb = dimLength;
            final int colNb = components.length / dimLength;
            this.matrix = newMatrix(rowNb, colNb);
            for (int i = 0; i < rowNb; i++) {
                for (int j = 0; j < colNb; j++) {
                    matrix[i][j] = components[i + rowNb * j];
                }
            }
        } else if (dim == Dimension.ROW) {
            final int colNb = dimLength;
            final int rowNb = components.length / dimLength;
            this.matrix = newMatrix(rowNb, colNb);
            for (int i = 0; i < rowNb; i++) {
                for (int j = 0; j < colNb; j++) {
                    matrix[i][j] = components[colNb * i + j];
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private K[][] newMatrix(final int rowNb, final int colNb) {
        return (K[][]) Array.newInstance(getField(), rowNb, colNb);
    }

    @Override
    public K[][] getMatrix() {
        return matrix;
    }

    @Override
    public int getDimension(final Dimension dim) {
        if (dim == Dimension.COLUMN) {
            return matrix[0].length;
        } else if (dim == Dimension.ROW) {
            return matrix.length;
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int[] getDimensions() {
        return new int[]{matrix.length, matrix[0].length};
    }

    @Override
    public K get(final int... i) {
        if (i.length != MATRIX_ORDER) {
            throw new IllegalArgumentException();
        }
        return matrix[i[0]][i[1]];
    }

    @Override
    public void set(final K c, final int... i) {
        if (i.length != MATRIX_ORDER) {
            throw new IllegalArgumentException();
        }
        matrix[i[0]][i[1]] = c;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final K[] row : matrix) {
            sb.append('[');
            final int length = row.length;
            for (int i = 0; i < length; i++) {
                sb.append(row[i]);
                if (i == length - 1) {
                    sb.append("]\n");
                } else {
                    sb.append(", ");
                }
            }
        }
        return sb.toString();
    }
}
