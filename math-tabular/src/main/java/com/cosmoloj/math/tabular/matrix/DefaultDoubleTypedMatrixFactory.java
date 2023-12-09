package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;

/**
 *
 * @author Samuel Andrés
 */
@Deprecated
public class DefaultDoubleTypedMatrixFactory {

    public TypedMatrix<Double> matrix(final Double[][] matrix) {
        return new DefaultDoubleTypedMatrix(matrix);
    }

    public TypedMatrix<Double> matrix(final Dimension dim, final int dimLength, final Double... components) {
        return new DefaultDoubleTypedMatrix(dim, dimLength, components);
    }

    public TypedMatrix<Double> rotation2D(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return matrix(Dimension.ROW, 2,
                cost, -sint,
                sint, cost);
    }

    public TypedMatrix<Double> rotation3DX(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return matrix(Dimension.ROW, 2,
                1.,   0.,   0.,
                0., cost, -sint,
                0., sint, cost);
    }

    public TypedMatrix<Double> rotation3DY(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return matrix(Dimension.ROW, 2,
                 cost, 0., sint,
                   0., 1.,   0.,
                -sint, 0., cost);
    }

    public TypedMatrix<Double> rotation3DZ(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return matrix(Dimension.ROW, 2,
                 cost, -sint, 0.,
                 sint,  cost, 0.,
                   0.,    0., 1.);
    }
}
