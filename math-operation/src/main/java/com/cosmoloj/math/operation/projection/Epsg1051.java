package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1051</div>
 * <div class="en">Lambert Conic Conformal (2SP Michigan)</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1051 implements InversibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double e;
    private final double phif;
    private final double lambdaf;
    private final double phi1;
    private final double phi2;
    private final double ef;
    private final double nf;
    private final double k;

    private final double f;
    private final double n;
    private final double m1;
    private final double m2;
    private final double t1;
    private final double t2;
    private final double rf;

    private final double precision = 1e-12;

    public Epsg1051(final Ellipsoid ellipsoid, final double phif, final double lambdaf, final double phi1,
            final double phi2, final double ef, final double nf, final double k) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.e = ellipsoid.e();
        this.phif = phif;
        this.lambdaf = lambdaf;
        this.phi1 = phi1;
        this.phi2 = phi2;
        this.ef = ef;
        this.nf = nf;
        this.k = k;
        this.m1 = m(phi1);
        this.m2 = m(phi2);
        this.t1 = t(phi1);
        this.t2 = t(phi2);
        this.n = n();
        this.f = f();
        this.rf = r(phif);
    }

    public static Epsg1051 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1051(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_FALSE_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_FALSE_ORIGIN),
            (double) params.get(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.LATITUDE_OF_2ND_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.EASTING_AT_FALSE_ORIGIN),
            (double) params.get(MethodParameter.NORTHING_AT_FALSE_ORIGIN),
            (double) params.get(MethodParameter.ELLIPSOID_SCALING_FACTOR));
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
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
            case ELLIPSOID_SCALING_FACTOR -> k;
            default -> throw new IllegalArgumentException();
        };
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
        return ef + r(phi) * Math.sin(theta(lambda));
    }

    double northing(final double phi, final double lambda) {
        return nf + rf - r(phi) * Math.cos(theta(lambda));
    }

    final double m(final double phi) {
        final double sinphi = Math.sin(phi);
        return Math.cos(phi) / Math.sqrt(1. - e * e * sinphi * sinphi);
    }

    final double t(final double phi) {
        final double esinphi = e * Math.sin(phi);
        return Math.tan(Math.PI / 4. - phi / 2.) / Math.pow((1. - esinphi) / (1. + esinphi), e / 2.);
    }

    final double n() {
        return (Math.log(m1) - Math.log(m2)) / (Math.log(t1) - Math.log(t2));
    }

    final double f() {
        return m1 / (n * Math.pow(t1, n));
    }

    final double r(final double phi) {
        final double t = t(phi);
        return t > 0. ? a * k * f * Math.pow(t, n) : 0.;
    }

    double theta(final double lambda) {
        return n * (lambda - lambdaf);
    }

    double lambda(final double easting, final double northing) {
        return invTheta(easting, northing) / n + lambdaf;
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
        return Math.atan2(easting - ef, rf - (northing - nf));
    }

    double invT(final double easting, final double northing) {
        return Math.pow(invR(easting, northing) / (a * k * f), 1. / n);
    }

    double invR(final double easting, final double northing) {
        final double relEasting = easting - ef;
        final double relNorthing = rf - (northing - nf);
        final double result = Math.sqrt(relEasting * relEasting + relNorthing * relNorthing);
        return n > 0. ? result : -result;
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_FALSE_ORIGIN,
                MethodParameter.LONGITUDE_OF_FALSE_ORIGIN,
                MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL,
                MethodParameter.LATITUDE_OF_2ND_STANDARD_PARALLEL,
                MethodParameter.EASTING_AT_FALSE_ORIGIN,
                MethodParameter.NORTHING_AT_FALSE_ORIGIN,
                MethodParameter.ELLIPSOID_SCALING_FACTOR);
    }
}
