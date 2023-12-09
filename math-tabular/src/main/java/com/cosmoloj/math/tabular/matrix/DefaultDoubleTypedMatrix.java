package com.cosmoloj.math.tabular.matrix;

/**
 *
 * @author Samuel Andrés
 */
public class DefaultDoubleTypedMatrix extends AbstractTypedMatrix<Double> {

    public DefaultDoubleTypedMatrix(final Double[][] matrix) {
        super(matrix);
    }

    protected DefaultDoubleTypedMatrix(final Dimension dim, final int dimLength, final Double... components) {
        super(dim, dimLength, components);
    }

    @Override
    public Class<Double> getField() {
        return Double.class;
    }

    public static TypedMatrix<Double> rows(final int r, final Double... values) {
        return new DefaultDoubleTypedMatrix(Dimension.ROW, r, values);
    }

    public static TypedMatrix<Double> columns(final int r, final Double... values) {
        return new DefaultDoubleTypedMatrix(Dimension.COLUMN, r, values);
    }
}
