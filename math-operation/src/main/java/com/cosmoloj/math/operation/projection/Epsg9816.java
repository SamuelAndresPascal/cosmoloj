package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.conversion.Conversion;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9816</div>
 * <div class="en">Tunisia Mining Grid</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9816 implements Conversion<double[], double[]>, AutoInverse<double[]> {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phif;
    private final double lambdaf;
    private final double ef;
    private final double nf;

    public Epsg9816(final Ellipsoid ellipsoid, final double phif, final double lambdaf, final double ef,
            final double nf) {
        this.ellipsoid = ellipsoid;
        this.phif = phif;
        this.lambdaf = lambdaf;
        this.ef = ef;
        this.nf = nf;
    }

    public static Epsg9816 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9816(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_FALSE_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_FALSE_ORIGIN),
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
            case LATITUDE_OF_FALSE_ORIGIN -> phif;
            case LONGITUDE_OF_FALSE_ORIGIN -> lambdaf;
            case EASTING_AT_FALSE_ORIGIN -> ef;
            case NORTHING_AT_FALSE_ORIGIN -> nf;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_FALSE_ORIGIN,
                MethodParameter.LONGITUDE_OF_FALSE_ORIGIN,
                MethodParameter.EASTING_AT_FALSE_ORIGIN,
                MethodParameter.NORTHING_AT_FALSE_ORIGIN);
    }

    @Override
    public double[] compute(final double[] input) {
        final double northing = input[NORTHING];
        final double easting = input[EASTING];
        final double a = (northing > nf) ? 0.010015 : 0.01002;
        return new double[]{
            phif + (northing - nf) * a,
            lambdaf + (easting - ef) * 0.012185
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];
        final double a = (phi > phif) ? 0.010015 : 0.01002;
        return new double[]{
            ef + (lambda - lambdaf) / 0.012185,
            nf + (phi - phif) / a
        };
    }
}
