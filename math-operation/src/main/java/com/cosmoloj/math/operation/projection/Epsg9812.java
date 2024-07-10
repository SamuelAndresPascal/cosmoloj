package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9812</div>
 * <div class="en">Hotine Oblique Mercator - Variant A</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9812 implements InvertibleProjection {

    protected static final int PHI = 0;
    protected static final int LAMBDA = 1;
    protected static final int EASTING = 0;
    protected static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phic;
    private final double lambdac;
    private final double alphac;
    private final double gammac;
    private final double kc;
    private final double fe;
    private final double fn;

    private final double e;
    private final double e2;
    private final double coefB;
    private final double a;
    private final double coefA;
    private final double t0;
    private final double coefD;
    private final double coefF;
    private final double coefH;
    private final double coefG;
    private final double gamma0;
    private final double lambda0;

    public Epsg9812(final Ellipsoid ellipsoid, final double phic, final double lambdac, final double alphac,
            final double gammac, final double kc, final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.phic = phic;
        this.lambdac = lambdac;
        this.alphac = alphac;
        this.gammac = gammac;
        this.kc = kc;
        this.fe = fe;
        this.fn = fn;

        this.e = ellipsoid.e();
        this.e2 = e * e;
        final double cosphic = Math.cos(phic);
        this.coefB = Math.sqrt(1. + (e2 * cosphic * cosphic * cosphic * cosphic / (1. - e2)));
        this.a = ellipsoid.a();
        final double esinphic = e * Math.sin(phic);
        this.coefA = a * coefB * kc * Math.sqrt(1. - e2) / (1. - esinphic * esinphic);
        this.t0 = t(phic);
        this.coefD = coefB * Math.sqrt(1. - e2) / (Math.cos(phic) * Math.sqrt(1. - esinphic * esinphic));
        this.coefF = computeCoefF();
        this.coefH = coefF * Math.pow(t0, coefB);
        this.coefG = (coefF - 1. / coefF) / 2.;
        this.gamma0 = Math.asin(Math.sin(alphac) / coefD);
        this.lambda0 = lambdac - (Math.asin(coefG * Math.tan(gamma0))) / coefB;
    }

    public static Epsg9812 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9812(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.LONGITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.AZIMUTH_OF_THE_INITIAL_LINE),
            (double) params.get(MethodParameter.ANGLE_FROM_THE_RECTIFIED_GRID_TO_THE_SKEW_GRID),
            (double) params.get(MethodParameter.SCALE_FACTOR_ON_THE_INITIAL_LINE),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    protected final double phic() {
        return phic;
    }

    protected final double alphac() {
        return alphac;
    }

    protected final double lambdac() {
        return lambdac;
    }

    protected final double gammac() {
        return gammac;
    }

    protected final double lambda0() {
        return lambda0;
    }

    protected final double coefA() {
        return coefA;
    }

    protected final double coefB() {
        return coefB;
    }

    protected final double coefD() {
        return coefD;
    }

    protected final double kc() {
        return kc;
    }

    protected final double fe() {
        return fe;
    }

    protected final double fn() {
        return fn;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_PROJECTION_CENTRE -> phic;
            case LONGITUDE_OF_PROJECTION_CENTRE -> lambdac;
            case AZIMUTH_OF_THE_INITIAL_LINE -> alphac;
            case ANGLE_FROM_THE_RECTIFIED_GRID_TO_THE_SKEW_GRID -> gammac;
            case SCALE_FACTOR_ON_THE_INITIAL_LINE -> kc;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.AZIMUTH_OF_THE_INITIAL_LINE,
                MethodParameter.ANGLE_FROM_THE_RECTIFIED_GRID_TO_THE_SKEW_GRID,
                MethodParameter.SCALE_FACTOR_ON_THE_INITIAL_LINE,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];

        final double u = u(phi, lambda);
        final double v = v(phi, lambda);

        return new double[]{
            v * Math.cos(gammac) + u * Math.sin(gammac) + fe,
            u * Math.cos(gammac) - v * Math.sin(gammac) + fn
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double easting = input[EASTING];
        final double northing = input[NORTHING];
        return new double[]{phi(easting, northing), lambda(easting, northing)};
    }

    final double invV(final double easting, final double northing) {
        return (easting - fe) * Math.cos(gammac) - (northing - fn) * Math.sin(gammac);
    }

    double invU(final double easting, final double northing) {
        return (northing - fn) * Math.cos(gammac) + (easting - fe) * Math.sin(gammac);
    }

    final double computeInvCoefQ(final double easting, final double northing) {
        return Math.exp(-coefB * invV(easting, northing) / coefA);
    }

    final double invCoefS(final double easting, final double northing) {
        final double q = computeInvCoefQ(easting, northing);
        return (q - 1. / q) / 2.;
    }

    double invCoefT(final double easting, final double northing) {
        final double q = computeInvCoefQ(easting, northing);
        return (q + 1. / q) / 2.;
    }

    double invCoefV(final double easting, final double northing) {
        return Math.sin(coefB * invU(easting, northing) / coefA);
    }

    double invCoefU(final double easting, final double northing) {
        return (invCoefV(easting, northing) * Math.cos(gamma0) + invCoefS(easting, northing) * Math.sin(gamma0))
                / invCoefT(easting, northing);
    }

    final double invT(final double easting, final double northing) {
        final double u = invCoefU(easting, northing);
        return Math.pow(coefH / Math.sqrt((1. + u) / (1. - u)), 1. / coefB);
    }

    final double invKhi(final double easting, final double northing) {
        return Math.PI / 2. - 2 * Math.atan(invT(easting, northing));
    }

    final double phi(final double easting, final double northing) {
        final double khi = invKhi(easting, northing);

        return khi + e2 * (Math.sin(2. * khi) * (1. / 2. + e2 * (5. / 24. + e2 * (1. / 12. + e2 * 13. / 360.)))
                + e2 * (Math.sin(4. * khi) * (7. / 48. + e2 * (29. / 240. + e2 * 811. / 11520.))
                + e2 * (Math.sin(6. * khi) * (7. / 120 + e2 * 81. / 1120.)
                + e2 * Math.sin(8. * khi) * 4279. / 161280.)));
    }

    final double lambda(final double easting, final double northing) {
        return lambda0
                - Math.atan2(invCoefS(easting, northing) * Math.cos(gamma0)
                        - invCoefV(easting, northing) * Math.sin(gamma0),
                        Math.cos(coefB * invU(easting, northing) / coefA))
                / coefB;
    }

    final double t(final double phi) {
        final double sinphi = e * Math.sin(phi);
        return Math.tan(Math.PI / 4. - phi / 2.) / Math.pow((1. - sinphi) / (1. + sinphi), e / 2.);
    }

    final double computeCoefQ(final double phi) {
        return coefH / Math.pow(t(phi), coefB);
    }

    final double computeCoefS(final double phi) {
        final double coefQ = computeCoefQ(phi);
        return (coefQ - 1. / coefQ) / 2.;
    }

    final double computeCoefT(final double phi) {
        final double coefQ = computeCoefQ(phi);
        return (coefQ + 1. / coefQ) / 2.;
    }

    double coefV(final double lambda) {
        return Math.sin(coefB * (lambda - lambda0));
    }

    double coefU(final double phi, final double lambda) {
        return (-coefV(lambda) * Math.cos(gamma0) + computeCoefS(phi) * Math.sin(gamma0)) / computeCoefT(phi);
    }

    double v(final double phi, final double lambda) {
        final double coefU = coefU(phi, lambda);
        return coefA * Math.log((1. - coefU) / (1. + coefU)) / (2. * coefB);
    }

    double u(final double phi, final double lambda) {
        return coefA
                * Math.atan2(computeCoefS(phi) * Math.cos(gamma0) + coefV(lambda) * Math.sin(gamma0),
                        Math.cos(coefB * (lambda - lambda0)))
                / coefB;
    }

    final double computeCoefF() {
        if (coefD < 1.) {
            return coefD;
        } else if (phic < 0) {
            return coefD - Math.sqrt(coefD * coefD - 1.);
        } else {
            return coefD + Math.sqrt(coefD * coefD - 1.);
        }
    }
}
