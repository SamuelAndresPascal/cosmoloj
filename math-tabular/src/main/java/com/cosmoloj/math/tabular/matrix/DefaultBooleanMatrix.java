package com.cosmoloj.math.tabular.matrix;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultBooleanMatrix implements BooleanMatrix {

    /**
     * First index is the row number. Second one is the column number.
     */
    private final boolean[][] matrix;

    public DefaultBooleanMatrix(final boolean[][] matrix) {
        this.matrix = matrix;
    }

    protected DefaultBooleanMatrix(final Dimension dim, final int dimLength, final boolean... components) {
        if (components.length % dimLength != 0) {
            throw new IllegalArgumentException();
        }

        if (dim == Dimension.COLUMN) {
            final int rowNb = dimLength;
            final int colNb = components.length / dimLength;
            this.matrix = new boolean[rowNb][colNb];
            for (int i = 0; i < rowNb; i++) {
                for (int j = 0; j < colNb; j++) {
                    matrix[i][j] = components[i + rowNb * j];
                }
            }
        } else if (dim == Dimension.ROW) {
            final int colNb = dimLength;
            final int rowNb = components.length / dimLength;
            this.matrix = new boolean[rowNb][colNb];
            for (int i = 0; i < rowNb; i++) {
                for (int j = 0; j < colNb; j++) {
                    matrix[i][j] = components[colNb * i + j];
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    protected DefaultBooleanMatrix(final Dimension dim, final int dimLength, final int... components) {
        if (components.length % dimLength != 0) {
            throw new IllegalArgumentException();
        }

        final boolean[] bComponents = new boolean[components.length];
        for (int i = 0; i < bComponents.length; i++) {
            final int c = components[i];
            if (c != 0 && c != 1) {
                throw new IllegalArgumentException();
            }
            bComponents[i] = (components[i] == 1);
        }

        if (dim == Dimension.COLUMN) {
            final int rowNb = dimLength;
            final int colNb = bComponents.length / dimLength;
            this.matrix = new boolean[rowNb][colNb];
            for (int i = 0; i < rowNb; i++) {
                for (int j = 0; j < colNb; j++) {
                    matrix[i][j] = bComponents[i + rowNb * j];
                }
            }
        } else if (dim == Dimension.ROW) {
            final int colNb = dimLength;
            final int rowNb = bComponents.length / dimLength;
            this.matrix = new boolean[rowNb][colNb];
            for (int i = 0; i < rowNb; i++) {
                for (int j = 0; j < colNb; j++) {
                    matrix[i][j] = bComponents[colNb * i + j];
                }
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean[][] getMatrix() {
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
    public void set(final boolean c, final int... i) {
        if (i.length != Matrix.MATRIX_ORDER) {
            throw new IllegalArgumentException();
        }
        matrix[i[0]][i[1]] = c;
    }

    @Override
    public boolean get(final int... i) {
        if (i.length != Matrix.MATRIX_ORDER) {
            throw new IllegalArgumentException();
        }
        return matrix[i[0]][i[1]];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (final boolean[] row : matrix) {
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

    public static BooleanMatrix rows(final int r, final int... values) {
        return new DefaultBooleanMatrix(Dimension.ROW, r, values);
    }

    public static BooleanMatrix columns(final int r, final int... values) {
        return new DefaultBooleanMatrix(Dimension.COLUMN, r, values);
    }

    public static BooleanMatrix rows(final int r, final boolean... values) {
        return new DefaultBooleanMatrix(Dimension.ROW, r, values);
    }

    public static BooleanMatrix columns(final int r, final boolean... values) {
        return new DefaultBooleanMatrix(Dimension.COLUMN, r, values);
    }
}
