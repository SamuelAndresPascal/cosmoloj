package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::9808</div>
 * <div class="en">Transverse Mercator (South Orientated)</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9808 extends Epsg9807usgs {

    public Epsg9808(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double k0,
            final double fe, final double fn) {
        super(ellipsoid, phi0, lambda0, k0, fe, fn);
    }

    public static Epsg9808 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9808(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public double[] compute(final double[] input) {
        final double[] output = super.compute(input);

        return new double[]{2. * fe() - output[EASTING], 2. * fn() - output[NORTHING]};
    }

    @Override
    double coefD(final double westing, final double nu1) {
        return -super.coefD(westing, nu1);
    }

    @Override
    double invM1(final double southing) {
        return m0() - (southing - fn()) / k0();
    }
}
