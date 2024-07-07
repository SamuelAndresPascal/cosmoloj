package com.cosmoloj.math.operation.perspective;

import com.cosmoloj.math.operation.projection.Projection;

/**
 *
 * @author Samuel Andrés
 */
public interface Perspective extends Projection {

    @Override
    default double[] compute(final double[] input) {
        return perspective(topocentric(input));
    }

    double[] topocentric(double[] input);

    double[] perspective(double[] topocentric);
}
