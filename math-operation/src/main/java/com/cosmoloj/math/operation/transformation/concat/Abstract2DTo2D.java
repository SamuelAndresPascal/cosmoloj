package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.math.operation.conversion.Epsg9659;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.InversibleTransformation;

/**
 *
 * @author Samuel Andrés
 *
 * @see Epsg9659
 */
abstract class Abstract2DTo2D extends Abstract3DTo3D {

    private final Epsg9659 sourceSide;
    private final Epsg9659 targetSide;

    protected Abstract2DTo2D(final Ellipsoid source, final Ellipsoid target,
            final InversibleTransformation<double[], double[]> kernel) {
        this(source, target, kernel, 0., 0.);
    }

    protected Abstract2DTo2D(final Ellipsoid source, final Ellipsoid target,
            final InversibleTransformation<double[], double[]> transform, final double inverseSourceHeight,
            final double inverseTargetHeight) {
        super(source, target, transform);
        this.sourceSide = new Epsg9659(source, inverseSourceHeight);
        this.targetSide = new Epsg9659(target, inverseTargetHeight);
    }

    @Override
    public double[] compute(final double[] input) {
        final double[] postInput = sourceSide.inverse(input);
        final double[] transformed = super.compute(postInput);
        return targetSide.compute(transformed);
    }

    @Override
    public double[] inverse(final double[] input) {
        final double[] postInput = targetSide.inverse(input);
        final double[] transformed = super.inverse(postInput);
        return sourceSide.compute(transformed);
    }
}
