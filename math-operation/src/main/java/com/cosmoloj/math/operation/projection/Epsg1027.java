package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1027</div>
 * <div class="en">Lambert Azimuthal Equal Area</div>
 *
 * @author Samuel Andrés
 */
@Cite({Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019, Cosmoloj.MAP_PROJECTIONS})
public class Epsg1027 implements InvertibleProjection {

    public enum Aspect {
        OBLIQUE, NORTH_POLE, SOUTH_POLE;
    }

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Aspect aspect;
    private final Spheroid sphere;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private final double r;

    public Epsg1027(final Spheroid sphere, final double phi0, final double lambda0, final double fe,
            final double fn) {
        this.sphere = sphere;

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

        this.r = sphere.r();
    }

    public static Epsg1027 ofParams(final Spheroid spheroid, final Map<MethodParameter, ?> params) {
        return new Epsg1027(spheroid,
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

        if (aspect.equals(Aspect.OBLIQUE)) {

            final double rkp = r * Math.sqrt(2. / (1. + Math.sin(phi0)
                    * Math.sin(phi) + Math.cos(phi0) * Math.cos(phi) * Math.cos(lambda - lambda0)));

            return new double[]{
                fe + rkp * Math.cos(phi) * Math.sin(lambda - lambda0),
                fn + rkp
                    * (Math.cos(phi0) * Math.sin(phi) - Math.sin(phi0) * Math.cos(phi) * Math.cos(lambda - lambda0))};
        } else {

            return new double[]{
                fe + 2. * r * Math.sin(lambda - lambda0) * (aspect.equals(Aspect.NORTH_POLE)
                    ? Math.sin(Math.PI / 4. - phi / 2.) : Math.cos(Math.PI / 4. - phi / 2.)),
                fn + 2. * r * Math.cos(lambda - lambda0) * (aspect.equals(Aspect.NORTH_POLE)
                    ? -Math.sin(Math.PI / 4. - phi / 2.) : Math.cos(Math.PI / 4. - phi / 2.))
            };
        }
     }

    @Override
    public double[] inverse(final double[] input) {
        final double easting = input[EASTING];
        final double northing = input[NORTHING];

        final double east = easting - fe;
        final double north = northing - fn;
        final double rho = Math.sqrt(east * east + north * north);

        if (rho < 1e-9) {
            return new double[]{phi0, lambda0};
        }

        final double c = 2. * Math.asin(rho / (2. * r));
        final double sinc = Math.sin(c);
        final double cosc = Math.cos(c);
        final double phi = Math.asin(cosc * Math.sin(phi0) + north * sinc * Math.cos(phi0) / rho);


        return new double[]{
            phi,
            lambda0 + switch (aspect) {
                case OBLIQUE -> Math.atan2(east * sinc, rho * Math.cos(phi0) * cosc - north * Math.sin(phi0) * sinc);
                case NORTH_POLE -> Math.atan2(easting - fe, fn - northing);
                default -> Math.atan2(easting - fe, northing - fn);
            }
        };
    }

    @Override
    public Spheroid getSurface() {
        return sphere;
    }
}
