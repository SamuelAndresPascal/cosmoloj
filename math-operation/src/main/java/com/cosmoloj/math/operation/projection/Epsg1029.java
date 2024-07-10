package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.math.operation.surface.Surface;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1029</div>
 * <div class="en">Equidistant Cylindrical (Spherical)</div>
 * <div class="en">See method code 1028 for ellipsoidal development. If the latitude of natural origin is at the
 * equator, also known as Plate Carrée. See also Pseudo Plate Carree, method code 9825.</div>
 *
 * @see Epsg1028
 * @see Epsg9825
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1029 implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Surface surface;
    private final double phi1;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private final double r;

    public Epsg1029(final Surface surface, final double phi1, final double lambda0, final double fe,
            final double fn) {
        this.surface = surface;
        this.phi1 = phi1;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;

        if (surface instanceof Spheroid spheroid) {
            this.r = spheroid.r();
        } else if (surface instanceof Ellipsoid ellipsoid) {
            this.r = ellipsoid.rc(phi1);
        } else {
            throw new IllegalArgumentException();
        }
    }

    public static Epsg1029 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1029(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_1ST_STANDARD_PARALLEL -> phi1;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public double[] compute(final double[] input) {

        return new double[]{fe + r * (input[LAMBDA] - lambda0) * Math.cos(phi1), fn + r * input[PHI]};
     }

    @Override
    public double[] inverse(final double[] input) {

        return new double[]{(input[NORTHING] - fn) / r, lambda0 + (input[EASTING] - fe) / r / Math.cos(phi1)};
    }

    @Override
    public Surface getSurface() {
        return surface;
    }
}
