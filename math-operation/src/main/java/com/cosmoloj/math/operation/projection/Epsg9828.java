package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::9828</div>
 * <div class="en">Bonne (South Orientated)</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9828 extends Epsg9827 {

    private static final int WESTING = 0;
    private static final int SOUTHING = 1;

    public Epsg9828(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        super(ellipsoid, phi0, lambda0, fe, fn);
    }

    public static Epsg9828 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9828(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];

        final double m = m(phi);

        final double coefM = getSurface().m(phi);

        final double rho = a() * m0() / Math.sin(phi0()) + coefM0() - coefM;

        final double coefT = a() * m * (lambda - lambda0()) / rho;

        return new double[]{
            rho * Math.sin(coefT) + fe(),
            (a() * m0() / Math.sin(phi0()) - rho * Math.cos(coefT)) + fn()
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double westing = input[WESTING];
        final double southing = input[SOUTHING];

        final double x = fe() - westing;
        final double y = fn() - southing;
        final double yRho = (a() * m0() / Math.sin(phi0()) - y);

        final double rho;

        if (phi0() < 0.) {
            rho = -Math.sqrt(x * x + yRho * yRho);
        } else {
            rho = Math.sqrt(x * x + yRho * yRho);
        }

        final double coefM = a() * m0() / Math.sin(phi0()) + coefM0() - rho;

        final double mu = getSurface().mu(coefM);

        final double phi = getSurface().phi1(mu);

        final double m = m(phi);

        final double preLambda;
        if (Math.abs(phi - Math.PI) < 1e-9 || Math.abs(phi + Math.PI) < 1e-9
                || Math.abs(m) < 1e-9) {
            preLambda = 0.;
        } else if (phi0() < 0.) {
            preLambda = Math.atan2(x, a() * m0() / Math.sin(phi0()) - y);
        } else {
            preLambda = Math.atan2(x, a() * m0() / Math.sin(phi0()) - y);
        }

        return new double[]{
            phi,
            lambda0() + rho * preLambda
        };
    }
}
