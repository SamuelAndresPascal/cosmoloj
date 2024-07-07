package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9837</div>
 * <div class="en">Geographic/topocentric conversions</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9837 implements CoordinateConversion, AutoInverse<double[]> {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int H = 2;
    private static final int U = 0;
    private static final int V = 1;
    private static final int W = 2;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double b;
    private final double e2;
    private final double phi0;
    private final double lambda0;
    private final double h0;

    private final double nu0;
    private final double nu0h0;
    private final double sinphi0;
    private final double cosphi0;
    private final double sinlambda0;
    private final double coslambda0;

    private final double x0;
    private final double y0;
    private final double z0;

    public Epsg9837(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double h0) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.b = ellipsoid.b();
        this.e2 = ellipsoid.e2();
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.sinlambda0 = Math.sin(lambda0);
        this.coslambda0 = Math.cos(lambda0);
        this.h0 = h0;
        this.nu0 = ellipsoid.nu(phi0);
        this.sinphi0 = Math.sin(phi0);
        this.cosphi0 = Math.cos(phi0);
        this.nu0h0 = h0 + nu0;
        this.x0 = nu0h0 * cosphi0 * coslambda0;
        this.y0 = nu0h0 * cosphi0 * sinlambda0;
        this.z0 = ((1. - e2) * nu0 + h0) * sinphi0;
    }

    public static Epsg9837 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9837(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_TOPOCENTRIC_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_TOPOCENTRIC_ORIGIN),
            (double) params.get(MethodParameter.ELLIPSOIDAL_HEIGHT_OF_TOPOCENTRIC_ORIGIN));
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_TOPOCENTRIC_ORIGIN -> phi0;
            case LONGITUDE_OF_TOPOCENTRIC_ORIGIN -> lambda0;
            case ELLIPSOIDAL_HEIGHT_OF_TOPOCENTRIC_ORIGIN -> h0;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_TOPOCENTRIC_ORIGIN,
                MethodParameter.LONGITUDE_OF_TOPOCENTRIC_ORIGIN,
                MethodParameter.ELLIPSOIDAL_HEIGHT_OF_TOPOCENTRIC_ORIGIN);
    }

    @Override
    public double[] compute(final double[] input) {

        final double phi = input[PHI];

        final double nu = ellipsoid.nu(phi);
        final double nuh = nu + input[H];
        final double sinphi = Math.sin(phi);
        final double cosphi = Math.cos(phi);
        final double l = input[LAMBDA] - lambda0;
        final double cosl = Math.cos(l);
        final double common = e2 * (nu0 * sinphi0 - nu * Math.sin(phi));

        return new double[]{
            nuh * cosphi * Math.sin(l),
            nuh * (sinphi * cosphi0 - cosphi * sinphi0 * cosl) + common * cosphi0,
            nuh * (sinphi * sinphi0 + cosphi * cosphi0 * cosl) + common * sinphi0 - nu0h0
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double u = input[U];
        final double v = input[V];
        final double w = input[W];

        final double x = x0 - u * sinlambda0 - v * sinphi0 * coslambda0 + w * cosphi0 * coslambda0;
        final double y = y0 + u * coslambda0 - v * sinphi0 * sinlambda0 + w * cosphi0 * sinlambda0;
        final double z = z0 + v * cosphi0 + w * sinphi0;

        final double p = Math.sqrt(x * x + y * y);
        final double q = Math.atan2(z * a, p * b);
        final double sinq = Math.sin(q);
        final double cosq = Math.cos(q);
        final double phi = Math.atan2(z + e2 / (1. - e2) * b * sinq * sinq * sinq, p - e2 * a * cosq * cosq * cosq);
        return new double[]{
            phi,
            Math.atan2(y, x),
            p / Math.cos(phi) - ellipsoid.nu(phi)
        };
    }
}
