package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::1043</div>
 * <div class="en">Krovak Modified (North Orientated)</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1043a extends Epsg1042a {

    public Epsg1043a(final Ellipsoid ellipsoid, final double phic, final double lambda0, final double alphac,
            final double phip, final double kp, final double ef, final double nf, final double x0, final double y0,
            final double c1, final double c2, final double c3, final double c4, final double c5, final double c6,
            final double c7, final double c8, final double c9, final double c10) {
        super(ellipsoid, phic, lambda0, alphac, phip, kp, ef, nf, x0, y0, c1, c2, c3, c4, c5, c6, c7, c8, c9, c10);
    }

    public static Epsg1043a ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1043a(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.LONGITUDE_OF_ORIGIN),
            (double) params.get(MethodParameter.COLATITUDE_OF_THE_CONE_AXIS),
            (double) params.get(MethodParameter.LATITUDE_OF_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING),
            (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT),
            (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT),
            (double) params.get(MethodParameter.C1),
            (double) params.get(MethodParameter.C2),
            (double) params.get(MethodParameter.C3),
            (double) params.get(MethodParameter.C4),
            (double) params.get(MethodParameter.C5),
            (double) params.get(MethodParameter.C6),
            (double) params.get(MethodParameter.C7),
            (double) params.get(MethodParameter.C8),
            (double) params.get(MethodParameter.C9),
            (double) params.get(MethodParameter.C10));
    }

    @Override
    public double[] compute(final double[] input) {
        final double[] original = super.compute(input);
        return new double[]{-original[WESTING], -original[SOUTHING]};
    }

    @Override
    public double[] inverse(final double[] input) {
        return super.inverse(new double[]{-input[WESTING], -input[SOUTHING]});
    }
}
