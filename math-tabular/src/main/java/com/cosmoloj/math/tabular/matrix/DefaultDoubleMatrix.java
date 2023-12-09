package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleMatrix implements DoubleMatrix {

    /**
     * First index is the row number. Second one is the column number.
     */
    private final double[][] matrix;

    public DefaultDoubleMatrix(final double[][] matrix) {
        this.matrix = DoubleTabulars.copy(matrix);
    }

    protected DefaultDoubleMatrix(final Dimension dim, final int dimLength, final double... components) {
        if (components.length % dimLength != 0) {
            throw new IllegalArgumentException();
        }

        if (dim == Dimension.COLUMN) {
            final int rowNb = dimLength;
            final int colNb = components.length / dimLength;
            this.matrix = new double[rowNb][colNb];
            for (int i = 0; i < rowNb; i++) {
                for (int j = 0; j < colNb; j++) {
                    matrix[i][j] = components[i + rowNb * j];
                }
            }
        } else if (dim == Dimension.ROW) {
            final int colNb = dimLength;
            final int rowNb = components.length / dimLength;
            this.matrix = new double[rowNb][colNb];
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
    public double[][] getMatrix() {
        return DoubleTabulars.copy(matrix);
    }

    public double[] getMatrix(final int i) {
        if (i < matrix.length) {
            return DoubleTabulars.copy(matrix[i]);
        }
        throw new IllegalArgumentException(
                "component " + i + " out of bounds for coordinate dimension " + matrix.length);
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
    public double get(final int... i) {
        if (i.length != MATRIX_ORDER) {
            throw new IllegalArgumentException();
        }
        return matrix[i[0]][i[1]];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final double[] row : matrix) {
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

    public static DoubleMatrix of(final double[][] matrix) {
        return new DefaultDoubleMatrix(matrix);
    }

    public static DoubleMatrix rows(final int r, final double... values) {
        return new DefaultDoubleMatrix(Dimension.ROW, r, values);
    }

    public static DoubleMatrix columns(final int r, final double... values) {
        return new DefaultDoubleMatrix(Dimension.COLUMN, r, values);
    }
}
