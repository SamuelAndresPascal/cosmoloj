package com.cosmoloj.math.operation.perspective;

import com.cosmoloj.math.operation.conversion.Epsg9837;
import com.cosmoloj.math.operation.surface.Ellipsoid;

/**
 *
 * @author Samuel Andrés
 *
 * @see Epsg9837
 */
abstract class AbstractPerspective implements Perspective {

    private final Epsg9837 geotopo;
    private final Ellipsoid ellipsoid;

    AbstractPerspective(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double h0) {
        this.ellipsoid = ellipsoid;
        this.geotopo = new Epsg9837(ellipsoid, phi0, lambda0, h0);
    }

    @Override
    public double[] topocentric(final double[] input) {
        return geotopo.compute(input);
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }
}
