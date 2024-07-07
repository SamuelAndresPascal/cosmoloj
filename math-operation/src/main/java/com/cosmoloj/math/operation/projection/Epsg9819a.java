package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9819</div>
 * <div class="en">Krovak</div>
 *
 * @author Samuel Andrés
 */
public class Epsg9819a implements InversibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    protected static final int EASTING = 0;
    protected static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double e;
    private final double phic;
    private final double lambda0;
    private final double alphac;
    private final double phip;
    private final double kp;
    private final double fe;
    private final double fn;

    private final double e2;
    private final double coefA;
    private final double coefB;
    private final double g0;
    private final double t0;
    private final double n;
    private final double r0;

    private final double precision = 1e-12;

    public Epsg9819a(final Ellipsoid ellipsoid, final double phic, final double lambda0, final double alphac,
            final double phip, final double kp, final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.e = ellipsoid.e();
        this.phic = phic;
        this.lambda0 = lambda0;
        this.alphac = alphac;
        this.phip = phip;
        this.kp = kp;
        this.fe = fe;
        this.fn = fn;

        this.e2 = e * e;
        this.coefA = coefA();
        this.coefB = coefB();
        this.g0 = g0();
        this.t0 = t0();
        this.n = n();
        this.r0 = r0();
    }

    public static Epsg9819a ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9819a(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.LONGITUDE_OF_ORIGIN),
            (double) params.get(MethodParameter.COLATITUDE_OF_THE_CONE_AXIS),
            (double) params.get(MethodParameter.LATITUDE_OF_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    protected final double alphac() {
        return alphac;
    }

    protected final double fe() {
        return fe;
    }

    protected final double fn() {
        return fn;
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_PROJECTION_CENTRE -> phic;
            case LONGITUDE_OF_ORIGIN -> lambda0;
            case COLATITUDE_OF_THE_CONE_AXIS -> alphac;
            case LATITUDE_OF_PSEUDO_STANDARD_PARALLEL -> phip;
            case SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL -> kp;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE,
                MethodParameter.LONGITUDE_OF_ORIGIN,
                MethodParameter.COLATITUDE_OF_THE_CONE_AXIS,
                MethodParameter.LATITUDE_OF_PSEUDO_STANDARD_PARALLEL,
                MethodParameter.SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public double[] compute(final double[] input) {
        final double coefU = coefU(input[PHI]);
        final double coefV = coefV(input[LAMBDA]);
        final double coefT = coefT(coefU, coefV);
        final double r = r(coefU, coefV, coefT);
        final double theta = theta(coefU, coefV, coefT);
        return new double[]{r * Math.cos(theta) + fn, r * Math.sin(theta) + fe};
    }

    @Override
    public double[] inverse(final double[] input) {
        final double invXp = invXp(input[EASTING]);
        final double invYp = invYp(input[NORTHING]);
        final double invT = invT(invXp, invYp);
        final double invD = invD(invXp, invYp);
        final double invU = invU(invT, invD);
        return new double[]{phi(invU), lambda(invT, invD, invU)};
    }

    final double coefA() {
        final double sinphic = Math.sin(phic);
        return a * Math.sqrt(1. - e2) / (1. - e2 * sinphic * sinphic);
    }

    final double coefB() {
        final double cosphic = Math.cos(phic);
        return Math.sqrt(1. + e2 * cosphic * cosphic * cosphic * cosphic / (1 - e2));
    }

    double coefU(final double phi) {
        final double esinphi = e * Math.sin(phi);
        return 2. * (Math.atan2(t0 * Math.pow(Math.tan(phi / 2. + Math.PI / 4.), coefB),
                Math.pow((1. + esinphi) / (1. - esinphi), e * coefB / 2.))
                - Math.PI / 4.);
    }

    double coefV(final double lambda) {
        return coefB * (lambda0 - lambda);
    }

    double coefT(final double coefU, final double coefV) {
        return Math.asin(Math.cos(alphac) * Math.sin(coefU) + Math.sin(alphac) * Math.cos(coefU) * Math.cos(coefV));
    }

    double coefD(final double coefU, final double coefV, final double coefT) {
        return Math.asin(Math.cos(coefU) * Math.sin(coefV) / Math.cos(coefT));
    }

    double theta(final double coefU, final double coefV, final double coefT) {
        return n * coefD(coefU, coefV, coefT);
    }

    double r(final double coefU, final double coefV, final double coefT) {
        return r0 * Math.pow(Math.tan(Math.PI / 4. + phip / 2.) / Math.tan(coefT / 2. + Math.PI / 4.), n);
    }

    final double g0() {
        return Math.asin(Math.sin(phic) / coefB);
    }

    final double t0() {
        final double sinphic = Math.sin(phic);
        return Math.tan(Math.PI / 4. + g0 / 2.)
                * Math.pow((1. + e * sinphic) / (1. - e * sinphic), e * coefB / 2.)
                / Math.pow(Math.tan(Math.PI / 4. + phic / 2.), coefB);
    }

    final double n() {
        return Math.sin(phip);
    }

    final double r0() {
        return kp * coefA / Math.tan(phip);
    }

    double lambda(final double invT, final double invD, final double invU) {
        return lambda0 - invV(invT, invD, invU) / coefB;
    }

    double phi(final double invU) {
        double phi = invU;

        while (true) {
            double tmp = phi(invU, phi);
            if (Math.abs(tmp - phi) > this.precision) {
                phi = tmp;
            } else {
                return tmp;
            }
        }
    }

    double phi(final double invU, final double phi) {
        final double esinphi = e * Math.sin(phi);
        return 2 * (Math.atan(Math.pow(Math.tan(invU / 2. + Math.PI / 4.) / t0, 1. / coefB)
                * Math.pow((1. + esinphi) / (1. - esinphi), e / 2.)) - Math.PI / 4.);
    }

    double invXp(final double southing) {
        return southing - fn;
    }

    double invYp(final double westing) {
        return westing - fe;
    }

    double invR(final double invXp, final double invYp) {
        return Math.sqrt(invXp * invXp + invYp * invYp);
    }

    double invTheta(final double invXp, final double invYp) {
        return Math.atan2(invYp, invXp);
    }

    double invD(final double invXp, final double invYp) {
        return invTheta(invXp, invYp) / Math.sin(phip);
    }

    double invT(final double invXp, final double invYp) {
        return 2. * (Math.atan(Math.pow(r0 / invR(invXp, invYp), 1. / n) * Math.tan(Math.PI / 4. +  phip / 2.))
                - Math.PI / 4.);
    }

    double invU(final double invT, final double invD) {
        return Math.asin(Math.cos(alphac) * Math.sin(invT) - Math.sin(alphac) * Math.cos(invT) *  Math.cos(invD));
    }

    double invV(final double invT, final double invD, final double invU) {
        return Math.asin(Math.cos(invT) *  Math.sin(invD) / Math.cos(invU));
    }
}
