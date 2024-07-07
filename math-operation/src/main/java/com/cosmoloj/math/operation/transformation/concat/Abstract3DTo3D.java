package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.conversion.Epsg9602;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.CoordinateTransformation;
import com.cosmoloj.math.operation.transformation.InversibleTransformation;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 *
 * @see Epsg9602
 */
abstract class Abstract3DTo3D implements CoordinateTransformation, AutoInverse<double[]> {

    private final InversibleTransformation<double[], double[]> kernel;
    private final Epsg9602 sourceSide;
    private final Epsg9602 targetSide;

    protected Abstract3DTo3D(final Ellipsoid source, final Ellipsoid target,
            final InversibleTransformation<double[], double[]> kernel) {
        this.sourceSide = new Epsg9602(source);
        this.targetSide = new Epsg9602(target);
        this.kernel = kernel;
    }


    @Override
    public double[] compute(final double[] input) {
        final double[] postInput = sourceSide.compute(input);
        final double[] transformed = kernel.compute(postInput);
        return targetSide.inverse(transformed);
    }

    @Override
    public double[] inverse(final double[] input) {
        final double[] postInput = targetSide.compute(input);
        final double[] transformed = kernel.inverse(postInput);
        return sourceSide.inverse(transformed);
    }

    @Override
    public List<MethodParameter> getParameters() {
        return kernel.getParameters();
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return kernel.getParameter(parameter);
    }
}
