package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::1041</div>
 * <div class="en">Krovak (North Orientated)</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1041a extends Epsg9819a {

    public Epsg1041a(final Ellipsoid ellipsoid, final double phic, final double lambda0, final double alphac,
            final double phip, final double kp, final double ef, final double nf) {
        super(ellipsoid, phic, lambda0, alphac, phip, kp, ef, nf);
    }

    public static Epsg1041a ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1041a(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.LONGITUDE_OF_ORIGIN),
            (double) params.get(MethodParameter.COLATITUDE_OF_THE_CONE_AXIS),
            (double) params.get(MethodParameter.LATITUDE_OF_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public double[] compute(final double[] input) {
        final double[] output = super.compute(input);
        return new double[]{-output[NORTHING], -output[EASTING]};
    }

    @Override
    public double[] inverse(final double[] input) {
        return super.inverse(new double[]{-input[NORTHING], -input[EASTING]});
    }
}
