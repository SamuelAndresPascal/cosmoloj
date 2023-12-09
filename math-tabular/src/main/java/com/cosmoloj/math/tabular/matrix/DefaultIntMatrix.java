package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultIntMatrix implements IntMatrix {

    /**
     * First index is the row number. Second one is the column number.
     */
    private final int[][] matrix;

    public DefaultIntMatrix(final int[][] matrix) {
        this.matrix = matrix;
    }

    protected DefaultIntMatrix(final Dimension dim, final int dimLength, final int... components) {
        if (components.length % dimLength != 0) {
            throw new IllegalArgumentException();
        }

        if (dim == Dimension.COLUMN) {
            final int rowNb = dimLength;
            final int colNb = components.length / dimLength;
            this.matrix = new int[rowNb][colNb];
            for (int i = 0; i < rowNb; i++) {
                for (int j = 0; j < colNb; j++) {
                    matrix[i][j] = components[i + rowNb * j];
                }
            }
        } else if (dim == Dimension.ROW) {
            final int colNb = dimLength;
            final int rowNb = components.length / dimLength;
            this.matrix = new int[rowNb][colNb];
            for (int i = 0; i < rowNb; i++) {
                for (int j = 0; j < colNb; j++) {
                    matrix[i][j] = components[colNb * i + j];
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int[][] getMatrix() {
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
    public void set(final int c, final int... i) {
        if (i.length != MATRIX_ORDER) {
            throw new IllegalArgumentException();
        }
        matrix[i[0]][i[1]] = c;
    }

    @Override
    public int get(final int... i) {
        if (i.length != MATRIX_ORDER) {
            throw new IllegalArgumentException();
        }
        return matrix[i[0]][i[1]];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final int[] row : matrix) {
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

    public static IntMatrix rows(final int r, final int... values) {
        return new DefaultIntMatrix(Dimension.ROW, r, values);
    }

    public static IntMatrix columns(final int r, final int... values) {
        return new DefaultIntMatrix(Dimension.COLUMN, r, values);
    }
}
