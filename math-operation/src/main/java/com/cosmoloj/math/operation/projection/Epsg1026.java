package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1026</div>
 * <div class="en">Mercator (Spherical)</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1026 extends MercatorSpherical implements InversibleProjection {

    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final double fe;
    private final double fn;

    public Epsg1026(final Spheroid spheroid, final double phi0, final double lambda0,
            final double fe, final double fn) {
        super(spheroid, phi0, lambda0);
        this.fe = fe;
        this.fn = fn;
    }

    public static Epsg1026 ofParams(final Spheroid spheroid, final Map<MethodParameter, ?> params) {
        return new Epsg1026(spheroid,
                (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
                (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
                (double) params.get(MethodParameter.FALSE_EASTING),
                (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_NATURAL_ORIGIN -> phi0();
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0();
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
        final double[] output = super.compute(input);
        return new double[]{fe + output[EASTING], fn + output[NORTHING]};
    }

    @Override
    public double[] inverse(final double[] input) {
        return super.inverse(new double[]{input[EASTING] - fe, input[NORTHING] - fn});
    }
}
