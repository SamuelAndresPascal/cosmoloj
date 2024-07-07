package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::9833</div>
 * <div class="en">Hyperbolic Cassini-Soldner</div>
 *
 * @author Samuel Andrés
 *
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9833 extends Epsg9806 {

    public Epsg9833(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        super(ellipsoid, phi0, lambda0, fe, fn);
    }

    public static Epsg9833 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9833(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public double[] compute(final double[] input) {
        final double[] output = super.compute(input);
        final double phi = input[PHI];
        final double x = x(phi, input[LAMBDA]);
        return new double[]{output[EASTING],
            fn() + x * (1. - x * x
                / (6. * getSurface().rho(phi) * getSurface().nu(phi)))};
    }

    @Override
    double invM1(final double northing) {
        return m0() + (northing - fn()) + invQ(northing);
    }

    double invPhi1p(final double northing) {
        return phi0() + (northing - fn()) / 315320.;
    }

    double invQp(final double northing, final double rho, final double nu) {
        final double f3 = northing - fn();
        return f3 * f3 * f3 / (6. * rho * nu);
    }

    double invQ(final double northing) {
        final double phi1p = invPhi1p(northing);
        final double rho = getSurface().rho(phi1p);
        final double nu = getSurface().nu(phi1p);
        final double f3 = northing - fn() + invQp(northing, rho, nu);
        return f3 * f3 * f3 / (6. * rho * nu);
    }
}
