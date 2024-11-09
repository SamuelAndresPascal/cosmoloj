package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9817</div>
 * <div class="en">Lambert Conic Near-Conformal</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9817a implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double phi0;
    private final double lambda0;
    private final double k0;
    private final double fe;
    private final double fn;

    private final double f;
    private final double n;
    private final double rho0;
    private final double nu0;

    private final double coefA;
    private final double coefAp;
    private final double coefBp;
    private final double coefCp;
    private final double coefDp;
    private final double coefEp;
    private final double r0;
    private final double s0;

    private final double precision = 1e-12;

    public Epsg9817a(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double k0,
            final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.k0 = k0;
        this.fe = fe;
        this.fn = fn;

        this.f = ellipsoid.f();
        this.n = n();
        this.rho0 = ellipsoid.rho(phi0);
        this.nu0 = ellipsoid.nu(phi0);

        this.coefA = computeCoefA();
        this.coefAp = computeCoefAp();
        this.coefBp = computeCoefBp();
        this.coefCp = computeCoefCp();
        this.coefDp = computeCoefDp();
        this.coefEp = computeCoefEp();
        this.r0 = computeR0();
        this.s0 = s(phi0);
    }

    protected final double phi0() {
        return phi0;
    }

    protected final double coefAp() {
        return coefAp;
    }

    protected final double coefBp() {
        return coefBp;
    }

    protected final double coefCp() {
        return coefCp;
    }

    protected final double coefDp() {
        return coefDp;
    }

    protected final double coefEp() {
        return coefEp;
    }

    protected final double s0() {
        return s0;
    }

    public static Epsg9817a ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9817a(ellipsoid,
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
        final double theta = theta(lambda);
        return fn + coefM(phi) + r(phi) * Math.sin(theta) * Math.tan(theta / 2.);
    }

    final double n() {
        return this.f / (2. - f);
    }

    final double computeCoefA() {
        return 1. /  (6. * rho0 * nu0);
    }

    final double computeCoefAp() {
        return a * (1. + n * (-1. + n * (1. - n) * (5. / 4. + 81. * n * n / 64.))) * Math.PI / 180.;
    }

    final double computeCoefBp() {
        return 3. * a * n * (1. + n * (-1. + 7. * n * (1. - n) / 8. + 55. * n * n * n / 64.)) / 2.;
    }

    final double computeCoefCp() {
        return 15. * a * n * n * (1. + n * (-1. + 3. * n * (1. - n) / 4.)) / 16.;
    }

    final double computeCoefDp() {
        return 35. * a * n * n * n * (1. + (n * (-1. + 11. * n / 16.))) / 48.;
    }

    final double computeCoefEp() {
        return 315. * a * n * n * n * n * (1. - n) / 512.;
    }

    final double computeR0() {
        return k0 * nu0 / Math.tan(phi0);
    }

    final double s(final double phi) {
        return Math.toDegrees(coefAp) * phi
                - coefBp * Math.sin(2. * phi)
                + coefCp * Math.sin(4. * phi)
                - coefDp * Math.sin(6. * phi)
                + coefEp * Math.sin(8. * phi);
    }

    final double m(final double phi) {
        return s(phi) - s0;
    }

    final double coefM(final double phi) {
        final double m = m(phi);
        return m * k0 * (1. + coefA * m * m);
    }

    final double r(final double phi) {
        return r0 - coefM(phi);
    }

    final double theta(final double lambda) {
        return (lambda - lambda0) * Math.sin(phi0);
    }

    final double lambda(final double easting, final double northing) {
        return lambda0 + invTheta(easting, northing) / Math.sin(phi0);
    }

    protected double phi(final double easting, final double northing) {
        final double mp = invMp(easting, northing);
        double phip = phi0 + mp / coefAp * (Math.PI / 180.);

        while (true) {
            double tmp = phi(easting, northing, mp, phip);
            if (Math.abs(tmp - phip) > this.precision) {
                phip = tmp;
            } else {
                return tmp;
            }
        }
    }

    private double phi(final double easting, final double northing, final double mp, final double phip) {
        return phip + (mp + s0
                - (coefAp * phip * (180. / Math.PI) - coefBp * Math.sin(2. * phip) + coefCp * Math.sin(4. * phip)
                - coefDp * Math.sin(6. * phip) + coefEp * Math.sin(8. * phip)))
                / coefAp * (Math.PI / 180.);
    }

    final double invTheta(final double easting, final double northing) {
        return Math.atan2(easting - fe, r0 - (northing - fn));
    }

    final double invR(final double easting, final double northing) {
        final double relEasting = easting - fe;
        final double relNorthing = r0 - (northing - fn);
        final double result = Math.sqrt(relEasting * relEasting + relNorthing * relNorthing);
        return phi0 > 0. ? result : -result;
    }

    protected final double invCoefM(final double easting, final double northing) {
        return r0 - invR(easting, northing);
    }

    protected double invMp(final double easting, final double northing) {
        final double invCoefM = invCoefM(easting, northing);
        double mp = invCoefM;

        while (true) {
            double tmp = invMp(invCoefM, mp, easting, northing);
            if (Math.abs(tmp - mp) > this.precision) {
                mp = tmp;
            } else {
                return tmp;
            }
        }
    }

    protected final double invMp(final double invCoefM, final double mp, final double easting, final double northing) {
        return mp - (invCoefM - k0 * mp * (1. + coefA * mp * mp)) / k0 / (-1 - 3 * coefA * mp * mp);
    }
}
