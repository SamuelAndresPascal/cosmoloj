package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9801</div>
 * <div class="en">Lambert Conic Conformal (1SP)</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9801 implements InversibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double e;
    private final double phi0;
    private final double lambda0;
    private final double k0;
    private final double fe;
    private final double fn;

    private final double f;
    private final double n;
    private final double r0;

    private final double precision = 1e-12;

    public Epsg9801(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double k0,
            final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.e = ellipsoid.e();
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.k0 = k0;
        this.fe = fe;
        this.fn = fn;
        this.n = n();
        this.f = f();
        this.r0 = r(phi0);
    }

    public static Epsg9801 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9801(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_NATURAL_ORIGIN -> phi0;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case SCALE_FACTOR_AT_NATURAL_ORIGIN -> k0;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];
        return new double[]{easting(phi, lambda), northing(phi, lambda)};
    }

    @Override
    public double[] inverse(final double[] input) {
        final double easting = input[EASTING];
        final double northing = input[NORTHING];
        return new double[]{phi(easting, northing), lambda(easting, northing)};
    }

    double easting(final double phi, final double lambda) {
        return fe + r(phi) * Math.sin(theta(lambda));
    }

    double northing(final double phi, final double lambda) {
        return fn + r0 - r(phi) * Math.cos(theta(lambda));
    }

    double m(final double phi) {
        final double sinphi = Math.sin(phi);
        return Math.cos(phi) / Math.sqrt(1. - e * e * sinphi * sinphi);
    }

    double t(final double phi) {
        final double esinphi = e * Math.sin(phi);
        return Math.tan(Math.PI / 4. - phi / 2.) / Math.pow((1. - esinphi) / (1. + esinphi), e / 2.);
    }

    final double n() {
        return Math.sin(phi0);
    }

    final double f() {
        return m(phi0) / (n * Math.pow(t(phi0), n));
    }

    final double r(final double phi) {
        final double t = t(phi);
        return t > 0. ? a * f * Math.pow(t, n) * k0 : 0.;
    }

    double theta(final double lambda) {
        return n * (lambda - lambda0);
    }

    double lambda(final double easting, final double northing) {
        return invTheta(easting, northing) / n + lambda0;
    }

    double phi(final double easting, final double northing) {
        double phi = invT(easting, northing);

        while (true) {
            double tmp = phi(easting, northing, phi);
            if (Math.abs(tmp - phi) > this.precision) {
                phi = tmp;
            } else {
                return tmp;
            }
        }
    }

    double phi(final double easting, final double northing, final double phi) {
        return Math.PI / 2. - 2. * Math.atan(
                invT(easting, northing) * Math.pow((1. - e * Math.sin(phi)) / (1 + e * Math.sin(phi)), e / 2.)
        );
    }

    double invTheta(final double easting, final double northing) {
        return Math.atan2(easting - fe, r0 - (northing - fn));
    }

    double invT(final double easting, final double northing) {
        return Math.pow(invR(easting, northing) / (a * f), 1. / n);
    }

    double invR(final double easting, final double northing) {
        final double relEasting = easting - fe;
        final double relNorthing = r0 - (northing - fn);
        final double result = Math.sqrt(relEasting * relEasting + relNorthing * relNorthing);
        return n > 0. ? result : -result;
    }
}
