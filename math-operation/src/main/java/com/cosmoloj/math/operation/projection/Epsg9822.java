package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9822</div>
 * <div class="en">Albers Equal Area</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9822 implements InversibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phi1;
    private final double phif;
    private final double lambdaf;
    private final double phi2;
    private final double ef;
    private final double nf;

    private final double a;
    private final double e;
    private final double e2;

    private final double alpha0;
    private final double alpha1;
    private final double alpha2;
    private final double m1;
    private final double m2;
    private final double n;
    private final double coefC;
    private final double rho0;

    public Epsg9822(final Ellipsoid ellipsoid, final double phif, final double lambdaf, final double phi1,
            final double phi2, final double ef, final double nf) {
        this.ellipsoid = ellipsoid;
        this.phi1 = phi1;
        this.phif = phif;
        this.lambdaf = lambdaf;
        this.phi2 = phi2;
        this.ef = ef;
        this.nf = nf;

        this.a = ellipsoid.a();
        this.e = ellipsoid.e();
        this.e2 = ellipsoid.e2();

        this.alpha0 = ellipsoid.q(phif);
        this.alpha1 = ellipsoid.q(phi1);
        this.alpha2 = ellipsoid.q(phi2);
        this.m1 = Math.cos(phi1) / ellipsoid.eSinSqrt(phi1);
        this.m2 = Math.cos(phi2) / ellipsoid.eSinSqrt(phi2);
        this.n = (m1 * m1 - m2 * m2) / (alpha2 - alpha1);
        this.coefC = m1 * m1 + n * alpha1;
        this.rho0 = rho(alpha0);
    }

    public static Epsg9822 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9822(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_FALSE_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_FALSE_ORIGIN),
            (double) params.get(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.LATITUDE_OF_2ND_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.EASTING_AT_FALSE_ORIGIN),
            (double) params.get(MethodParameter.NORTHING_AT_FALSE_ORIGIN));
    }

    private double rho(final double alpha) {
        return a * Math.sqrt(coefC - n * alpha) / n;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_FALSE_ORIGIN -> phif;
            case LONGITUDE_OF_FALSE_ORIGIN -> lambdaf;
            case LATITUDE_OF_1ST_STANDARD_PARALLEL -> phi1;
            case LATITUDE_OF_2ND_STANDARD_PARALLEL -> phi2;
            case EASTING_AT_FALSE_ORIGIN -> ef;
            case NORTHING_AT_FALSE_ORIGIN -> nf;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_FALSE_ORIGIN,
                MethodParameter.LONGITUDE_OF_FALSE_ORIGIN,
                MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL,
                MethodParameter.LATITUDE_OF_2ND_STANDARD_PARALLEL,
                MethodParameter.EASTING_AT_FALSE_ORIGIN,
                MethodParameter.NORTHING_AT_FALSE_ORIGIN);
    }

    double[] computePolar(final double[] input) {

        final double theta = n * (input[LAMBDA] - lambdaf);
        final double rho = rho(ellipsoid.q(input[PHI]));

        return new double[]{rho, theta};
     }

    double[] inversePolar(final double[] input) {
        final double rho = input[0];
        final double theta = input[1];

        final double alphap = (coefC - (rho * rho * n * n / a / a)) / n;

        final double betap = Math.asin(alphap / (1. - ((1. - e2) / (2. * e) * Math.log((1. - e) / (1. + e)))));

        return new double[]{
            ellipsoid.phi(betap),
            lambdaf + theta / n
        };
    }

    @Override
    public double[] compute(final double[] input) {

        final double[] polar = computePolar(input);

        final double rho = polar[0];
        final double theta = polar[1];

        return new double[]{
            ef + rho * Math.sin(theta),
            nf + rho0 - rho * Math.cos(theta)
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double east = input[EASTING] - ef;
        final double north = rho0 - (input[NORTHING] - nf);

        final double theta;
        if (n < 0.) {
            theta = Math.atan2(-east, -north);
        } else {
            theta = Math.atan2(east, north);
        }

        final double rho = Math.sqrt(east * east + north * north);
        return inversePolar(new double[]{rho, theta});
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }
}
