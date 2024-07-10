package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9820</div>
 * <div class="en">Lambert Azimuthal Equal Area</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9820 implements InvertibleProjection {

    private enum Aspect {
        OBLIQUE, NORTH_POLE, SOUTH_POLE;
    }

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Aspect aspect;
    private final Ellipsoid ellipsoid;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private final double ellA;
    private final double betapDenominator;
    private final double e2;
    private final double qp;
    private final double q0;
    private final double sinbeta0;
    private final double cosbeta0;
    private final double rq;
    private final double coefD;

    public Epsg9820(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;

        if (Math.abs(phi0 - Math.PI / 2.) < 1e-9) {
            aspect = Aspect.NORTH_POLE;
        } else if (Math.abs(phi0 + Math.PI / 2.) < 1e-9) {
            aspect = Aspect.SOUTH_POLE;
        } else {
            aspect = Aspect.OBLIQUE;
        }

        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;

        this.ellA = ellipsoid.a();
        final double e = ellipsoid.e();
        this.e2 = e * e;
        this.betapDenominator = ellA * (1. - (1. - e2) / (2. * e) * Math.log((1. + e) / (1. - e)));
        this.qp = ellipsoid.qp();
        this.q0 = ellipsoid.q(phi0);
        final double beta0 = Math.asin(q0 / qp);
        this.sinbeta0 = Math.sin(beta0);
        this.cosbeta0 = Math.cos(beta0);
        this.rq = ellipsoid.rq();
        this.coefD = ellA
                * (Math.cos(phi0) / Math.sqrt(1. - e2 * Math.sin(phi0) * Math.sin(phi0))) / (rq * Math.cos(beta0));
    }

    public static Epsg9820 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9820(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
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

        final double q = ellipsoid.q(phi);

        if (aspect.equals(Aspect.OBLIQUE)) {

            final double sinbeta = q / qp;
            final double cosbeta = Math.cos(Math.asin(q / qp));
            final double coefB = rq
                    * Math.sqrt(2. / (1. + sinbeta0 * sinbeta + (cosbeta0 * cosbeta * Math.cos(lambda - lambda0))));

            return new double[]{
                fe + coefB * coefD * cosbeta * Math.sin(lambda - lambda0),
                fn + coefB / coefD * (cosbeta0 * sinbeta - sinbeta0 * cosbeta * Math.cos(lambda - lambda0))};
        } else {
            final double rho = ellA * Math.sqrt(qp - q);

            return new double[]{
                fe + rho * Math.sin(lambda - lambda0),
                fn + rho * (aspect.equals(Aspect.NORTH_POLE) ? -Math.cos(lambda - lambda0) : Math.cos(lambda - lambda0))
            };
        }
     }

    @Override
    public double[] inverse(final double[] input) {
        final double easting = input[EASTING];
        final double northing = input[NORTHING];


        if (aspect.equals(Aspect.OBLIQUE)) {
            final double east = (easting - fe) / coefD;
            final double north = (northing - fn) * coefD;
            final double rho = Math.sqrt(east * east + north * north);
            final double coefC = 2. * Math.asin(rho / (2. * rq));
            final double sinc = Math.sin(coefC);
            final double cosc = Math.cos(coefC);
            final double betap = Math.asin((cosc * sinbeta0) + (north * sinc * cosbeta0 / rho));

            return new double[]{
                ellipsoid.phi(betap),
                lambda0 + Math.atan2((easting - fe) * sinc,
                        coefD * rho * cosbeta0 * cosc - coefD * coefD * (northing - fn) * sinbeta0 * sinc)
            };
        } else {
            final double east = (easting - fe);
            final double north = (northing - fn);
            final double rho = Math.sqrt(east * east + north * north);
            final double betap = Math.asin(1. - rho * rho / betapDenominator);

            if (aspect.equals(Aspect.NORTH_POLE)) {
                return new double[] {
                    ellipsoid.phi(betap),
                    lambda0 + Math.atan2(easting - fe, fn - northing)
                };
            } else  {
                return new double[] {
                    ellipsoid.phi(-betap),
                    lambda0 + Math.atan2(easting - fe, northing - fn)
                };
            }
        }
    }

    double coefC(final double phi) {
        return Math.tan(phi) / ellipsoid.nu(phi) * ellipsoid.a();
    }

    double phin(final double phin, final double a, final double b, final double c) {
        final double ma = ellipsoid.m(phin) / ellipsoid.a();
        final double mnp = ellipsoid.mp(phin);
        return phin - (a  * (c * ma + 1.) - ma - 1. / 2. * (ma * ma + b) * c)
                / (e2 * Math.sin(2. * phin) * (ma * ma + b - 2. * a * ma) / (4. * c)
                + (a - ma) * (c * mnp - 2. / Math.sin(2. * phin)) - mnp);
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }
}
