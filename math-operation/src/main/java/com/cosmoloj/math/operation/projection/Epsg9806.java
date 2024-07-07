package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9806</div>
 * <div class="en">Cassini-Soldner</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9806 implements InversibleProjection {

    protected static final int PHI = 0;
    protected static final int LAMBDA = 1;
    protected static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private final double e2;
    private final double m0;

    public Epsg9806(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;

        this.e2 = ellipsoid.e() * ellipsoid.e();
        this.m0 = ellipsoid.m(phi0);
    }

    protected final double fn() {
        return fn;
    }

    protected final double phi0() {
        return phi0;
    }

    protected final double m0() {
        return m0;
    }

    public static Epsg9806 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9806(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public final Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_NATURAL_ORIGIN -> phi0;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];
        final double coefA = coefA(phi, lambda);
        final double coefA2 = coefA * coefA;
        final double coefT = coefT(phi);
        return new double[]{
            fe + ellipsoid.nu(phi) * coefA
                * (1. - coefA2 * coefT * (1. / 6. + (8. - coefT + 8. * coefC(phi)) * coefA2 / 120.)),
            fn + x(phi, lambda)};
    }

    @Override
    public double[] inverse(final double[] input) {
        final double phi1 = ellipsoid.phi1(ellipsoid.rectifyingLatitude(invM1(input[NORTHING])));
        final double t = invCoefT1(phi1);
        final double nu = ellipsoid.nu(phi1);
        final double rho = ellipsoid.rho(phi1);
        final double coefD = (input[EASTING] - fe) / nu;
        final double coefD2 = coefD * coefD;
        return new double[]{
            phi1 - (nu * Math.tan(phi1) / rho) * coefD2 * (1. / 2. - (1. - 3. * t) * coefD2 / 24.),
            lambda0 + coefD * (1. + coefD2 * t * (-1. / 3. + (1. + 3 * t) * coefD2 / 15.)) / Math.cos(phi1)};
    }

    double invCoefT1(final double phi1) {
        final double tanphi = Math.tan(phi1);
        return tanphi * tanphi;
    }

    double invM1(final double northing) {
        return m0 + (northing - fn);
    }

    double x(final double phi, final double lambda) {
        final double coefA = coefA(phi, lambda);
        final double a2 = coefA * coefA;
        return ellipsoid.m(phi) - m0 + ellipsoid.nu(phi) * Math.tan(phi) * a2
                * (1. / 2. + (5. - coefT(phi) + 6. * coefC(phi)) * a2 / 24.);
    }

    double coefA(final double phi, final double lambda) {
        return (lambda - lambda0) * Math.cos(phi);
    }

    double coefT(final double phi) {
        final double tan = Math.tan(phi);
        return tan * tan;
    }

    double coefC(final double phi) {
        final double cosphi = Math.cos(phi);
        return e2 * cosphi * cosphi / (1. - e2);
    }
}
