package com.cosmoloj.math.tabular.matrix;

import com.cosmoloj.math.tabular.matrix.Matrix.Dimension;

/**
 *
 * @author Samuel Andrés
 */
@Deprecated
public class DefaultDoubleMatrixFactory {

    public DoubleMatrix get(final double[][] matrix) {
        return new DefaultDoubleMatrix(matrix);
    }

    public DoubleMatrix get(final Dimension dim, final int dimLength, final double... components) {
        return new DefaultDoubleMatrix(dim, dimLength, components);
    }

    final DoubleMatrix rotation2D(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return get(Dimension.ROW, 2,
                cost, -sint,
                sint, cost);
    }

    final DoubleMatrix rotation3DX(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return get(Dimension.ROW, 2,
                1.,   0.,   0.,
                0., cost, -sint,
                0., sint, cost);
    }

    final DoubleMatrix rotation3DY(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return get(Dimension.ROW, 2,
                 cost, 0., sint,
                   0., 1.,   0.,
                -sint, 0., cost);
    }

    final DoubleMatrix rotation3DZ(final double theta) {
        final double cost = Math.cos(theta);
        final double sint = Math.sin(theta);
        return get(Dimension.ROW, 2,
                 cost, -sint, 0.,
                 sint,  cost, 0.,
                   0.,    0., 1.);
    }
}
