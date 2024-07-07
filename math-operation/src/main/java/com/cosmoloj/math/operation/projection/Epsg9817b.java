package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::9817</div>
 * <div class="en">Lambert Conic Near-Conformal</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9817b extends Epsg9817a implements InversibleProjection {

    public Epsg9817b(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double k0,
            final double fe, final double fn) {
        super(ellipsoid, phi0, lambda0, k0, fe, fn);
    }

    public static Epsg9817b ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9817b(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    protected double phi(final double easting, final double northing) {
        final double mp = invMp(easting, northing);
        final double phip = phi0() + mp / coefAp() * (Math.PI / 180.);
        final double sp = s(phip);
        final double dsp = coefAp() * 180. / Math.PI
                - 2. * coefBp() * Math.cos(2. * phip)
                + 4. * coefCp() * Math.cos(4. * phip)
                - 6. * coefDp() * Math.cos(6. * phip)
                + 8. * coefEp() * Math.cos(8. * phip);
        return phip - ((mp + s0() - sp) / -dsp);
    }

    @Override
    protected double invMp(final double easting, final double northing) {
        final double invCoefM = invCoefM(easting, northing);
        return invMp(invCoefM, invCoefM, easting, northing);
    }
}
