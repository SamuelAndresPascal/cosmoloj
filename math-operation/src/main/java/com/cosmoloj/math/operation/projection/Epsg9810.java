package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9810</div>
 * <div class="en">Polar Stereographic - Variant A</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg9829
 * @see Epsg9830
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9810 implements InvertibleProjection {

    protected static final int PHI = 0;
    protected static final int LAMBDA = 1;
    protected static final int EASTING = 0;
    protected static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final Aspect phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;
    private final double k0;

    private final double e;
    private final double e2;
    private final double eak0;

    public Epsg9810(final Ellipsoid ellipsoid, final Aspect phi0, final double lambda0, final double k0,
            final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;
        this.k0 = k0;

        this.e = ellipsoid.e();
        this.e2 = e * e;
        this.eak0 = Math.sqrt(Math.pow(1. + e, 1. + e) * Math.pow(1. - e, 1. - e))
                / (2. * ellipsoid.a() * k0);
    }

    public static Epsg9810 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9810(ellipsoid,
            // on interprète la latitude au sens large sans se préoccuper de la distance au pôle
            Aspect.interpret((double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN)),
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

        final double theta = lambda - lambda0;
        final double t = t(phi);
        final double rho = t / eak0;

        return new double[]{
            fe + rho * Math.sin(theta),
            switch (phi0) {
                case SOUTH_POLE -> fn + rho * Math.cos(theta);
                case NORTH_POLE -> fn - rho * Math.cos(theta);
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
            lambda0 + Math.atan2(easting - fe, switch (phi0) {
                case SOUTH_POLE -> northing - fn;
                case NORTH_POLE -> fn - northing;
                default -> throw new IllegalStateException();
            })
        };

    }

    double khi(final double easting, final double northing) {
        final double re = easting - fe;
        final double rn = northing - fn;
        final double rho = Math.sqrt(re * re + rn * rn);
        final double t = rho * eak0;
        return switch (phi0) {
            case SOUTH_POLE -> 2. * Math.atan(t) - Math.PI / 2.;
            case NORTH_POLE -> Math.PI / 2. - 2. * Math.atan(t);
            default -> throw new IllegalStateException();
        };
    }

    double t(final double phi) {
        return tCore(
                switch (phi0) {
                    case SOUTH_POLE -> phi;
                    case NORTH_POLE -> -phi;
                    default -> throw new IllegalStateException();
                });
    }

    double tCore(final double phi) {
        final double esinphi = e * Math.sin(phi);
        return Math.tan(Math.PI / 4. + phi / 2.) / Math.pow((1. + esinphi) / (1. - esinphi), e / 2.);
    }
}
