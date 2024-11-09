package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9835</div>
 * <div class="en">Lambert Cylindrical Equal Area</div>
 *
 * @author Samuel Andrés
 *
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9835 extends CylindricalEqualAreaEllipsoid.Normal {

    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final double phi1;
    private final double fe;
    private final double fn;

    public Epsg9835(final Ellipsoid ellipsoid, final double phi1, final double lambda0, final double fe,
            final double fn) {
        super(ellipsoid, phi1, lambda0);
        this.fe = fe;
        this.fn = fn;
        this.phi1 = phi1;
    }

    public static Epsg9835 ofParams(final Ellipsoid spheroid, final Map<MethodParameter, ?> params) {
        return new Epsg9835(spheroid,
            (double) params.get(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public double[] compute(final double[] input) {
        final double[] original = super.compute(input);
        return new double[]{fe + original[EASTING], fn + original[NORTHING]};
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_1ST_STANDARD_PARALLEL -> phi1;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0();
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
    public double[] inverse(final double[] input) {
        return super.inverse(new double[]{input[NORTHING] - fn, input[EASTING] - fe});
    }
}
