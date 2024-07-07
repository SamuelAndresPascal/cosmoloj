package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9830</div>
 * <div class="en">Polar Stereographic - Variant C</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg9810
 * @see Epsg9829
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9830 implements InversibleProjection {

    protected static final int PHI = 0;
    protected static final int LAMBDA = 1;
    protected static final int EASTING = 0;
    protected static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final Aspect aspect;
    private final double phif;
    private final double lambda0;
    private final double ef;
    private final double nf;

    private final double e;
    private final double e2;
    private final double tf;
    private final double rhof;

    public Epsg9830(final Ellipsoid ellipsoid, final double phif, final double lambda0, final double ef,
            final double nf) {
        this.ellipsoid = ellipsoid;
        this.phif = phif;
        this.aspect = Aspect.interpret(phif);
        this.lambda0 = lambda0;
        this.ef = ef;
        this.nf = nf;

        this.e = ellipsoid.e();
        this.e2 = e * e;

        this.tf = t(phif);
        final double mf = Math.cos(phif) / Math.sqrt(1. - e2 * Math.sin(phif) * Math.sin(phif));
        this.rhof = ellipsoid.a() * mf;
    }

    public static Epsg9830 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9830(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.LONGITUDE_OF_ORIGIN),
            (double) params.get(MethodParameter.EASTING_AT_FALSE_ORIGIN),
            (double) params.get(MethodParameter.NORTHING_AT_FALSE_ORIGIN));
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_STANDARD_PARALLEL -> phif;
            case LONGITUDE_OF_ORIGIN -> lambda0;
            case EASTING_AT_FALSE_ORIGIN -> ef;
            case NORTHING_AT_FALSE_ORIGIN -> nf;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_STANDARD_PARALLEL,
                MethodParameter.LONGITUDE_OF_ORIGIN,
                MethodParameter.EASTING_AT_FALSE_ORIGIN,
                MethodParameter.NORTHING_AT_FALSE_ORIGIN);
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];

        final double theta = lambda - lambda0;
        final double t = t(phi);
        final double rho = rhof * t / tf;

        return new double[]{
            ef + rho * Math.sin(theta),
            switch (aspect) {
                case SOUTH_POLE -> nf - rhof + rho * Math.cos(theta);
                case NORTH_POLE -> nf + rhof - rho * Math.cos(theta);
                default -> throw new IllegalStateException();
            }
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double northing = input[NORTHING];
        final double easting = input[EASTING];
        final double khi = khi(easting, northing);

        return new double[]{
            khi + e2 * ((1. / 2. + e2 * (5. / 24. + e2 * (1. / 12. + e2 * 13. / 360.))) * Math.sin(2. * khi)
                + e2 * ((7. / 48. + e2 * (29. / 240. + e2 * 811. / 11520.)) * Math.sin(4. * khi)
                + e2 * ((7. / 120. + e2 * (81. / 1120.)) * Math.sin(6. * khi)
                + e2 * 4279. / 161280. * Math.sin(8. * khi)))),
            lambda0 + Math.atan2(easting - ef, switch (aspect) {
                case SOUTH_POLE -> northing - nf + rhof;
                case NORTH_POLE -> nf + rhof - northing;
                default -> throw new IllegalStateException();
            })
        };

    }

    double khi(final double easting, final double northing) {
        final double re = easting - ef;
        final double rn = switch (aspect) {
            case SOUTH_POLE -> northing - nf + rhof;
            case NORTH_POLE -> northing - nf - rhof;
            default -> throw new IllegalStateException();
        };
        final double rho = Math.sqrt(re * re + rn * rn);
        final double t = rho * tf / rhof;
        return switch (aspect) {
            case SOUTH_POLE -> 2. * Math.atan(t) - Math.PI / 2.;
            case NORTH_POLE -> Math.PI / 2. - 2. * Math.atan(t);
            default -> throw new IllegalStateException();
        };
    }

    final double t(final double phi) {
        return tCore(
                switch (aspect) {
                    case SOUTH_POLE -> phi;
                    case NORTH_POLE -> -phi;
                    default -> throw new IllegalStateException();
                });
    }

    final double tCore(final double phi) {
        final double esinphi = e * Math.sin(phi);
        return Math.tan(Math.PI / 4. + phi / 2.) / Math.pow((1. + esinphi) / (1. - esinphi), e / 2.);
    }
}
