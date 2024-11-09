package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9807</div>
 * <div class="en">Transverse Mercator</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9807usgs implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    protected static final int EASTING = 0;
    protected static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;
    private final double k0;

    private final double e2;
    private final double ep2;
    private final double m0;

    public Epsg9807usgs(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double k0,
            final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;
        this.k0 = k0;

        this.e2 = ellipsoid.e() * ellipsoid.e();
        this.ep2 = ellipsoid.secondE() * ellipsoid.secondE();
        this.m0 = ellipsoid.m(phi0);
    }

    public static Epsg9807usgs ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9807usgs(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public final Ellipsoid getSurface() {
        return ellipsoid;
    }

    protected final double fe() {
        return fe;
    }

    protected final double fn() {
        return fn;
    }

    protected final double k0() {
        return k0;
    }

    protected final double m0() {
        return m0;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_NATURAL_ORIGIN -> phi0;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            case SCALE_FACTOR_AT_NATURAL_ORIGIN -> k0;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING,
                MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN);
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];

        final double nu = ellipsoid.nu(phi);

        final double coefA = coefA(phi, input[LAMBDA]);
        final double coefA2 = coefA * coefA;

        final double coefT = coefT(phi);
        final double coefC = coefC(phi);

        return new double[]{
            fe + k0 * nu * coefA * (1.
                + coefA2 * ((1. - coefT + coefC) / 6.
                + coefA2 * (5. + coefT * (coefT - 18.) + 72. * coefC - 58. * ep2) / 120.)),
            fn + k0 * (ellipsoid.m(phi) - m0 + nu * Math.tan(phi)
                * (coefA2 * (1. / 2.
                + coefA2 * ((5. - coefT + coefC * (9. + 4. * coefC)) / 24.
                + coefA2 * (61. + coefT * (-58 + coefT) + 600 * coefC - 330 * ep2) / 720.))))};
    }

    @Override
    public double[] inverse(final double[] input) {

        final double phi1 = ellipsoid.phi1(ellipsoid.rectifyingLatitude(invM1(input[NORTHING])));

        final double nu1 = ellipsoid.nu(phi1);
        final double rho1 = ellipsoid.rho(phi1);

        final double coefD = coefD(input[EASTING], nu1);
        final double coefD2 = coefD * coefD;
        final double coefT = coefT(phi1);
        final double coefC = coefC(phi1);

        return new double[]{
            phi1 - nu1 * Math.tan(phi1) / rho1
                * coefD2 * (1. / 2.
                + coefD2 * (-(5 + 3. * coefT + coefC * (10. - 4. * coefC) - 9. * ep2) / 24.
                + coefD2 * (61. + coefT * (90. + 45 * coefT) + coefC * (298. - 3. * coefC) - 252. * ep2) / 720.)),
            lambda0 + coefD * (1.
                + coefD2 * (-(1. + 2 * coefT + coefC) / 6.
                + coefD2 * (5. - coefC * (2. + 3 * coefC) + coefT * (28. + 24 * coefT) + 8. * ep2) / 120.))
                / Math.cos(phi1)
        };
    }

    final double coefT(final double phi) {
        final double tan = Math.tan(phi);
        return tan * tan;
    }

    final double coefC(final double phi) {
        final double cos = Math.cos(phi);
        return e2 * cos * cos / (1. - e2);
    }

    final double coefA(final double phi, final double lambda) {
        return (lambda - lambda0) * Math.cos(phi);
    }

    double coefD(final double easting, final double nu1) {
        return (easting - fe) / (nu1 * k0);
    }

    double invM1(final double northing) {
        return m0 + (northing - fn) / k0;
    }
}
