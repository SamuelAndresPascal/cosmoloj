package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import java.util.Map;

/**
 * <div>EPSG::9819</div>
 * <div class="en">Krovak</div>
 *
 * @author Samuel Andrés
 */
public class Epsg9819b extends Epsg9819a {

    public Epsg9819b(final Ellipsoid ellipsoid, final double phic, final double lambda0, final double alphac,
            final double phip, final double kp, final double ef, final double nf) {
        super(ellipsoid, phic, lambda0, alphac, phip, kp, ef, nf);
    }

    public static Epsg9819b ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9819b(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.LONGITUDE_OF_ORIGIN),
            (double) params.get(MethodParameter.COLATITUDE_OF_THE_CONE_AXIS),
            (double) params.get(MethodParameter.LATITUDE_OF_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.EASTING_AT_FALSE_ORIGIN),
            (double) params.get(MethodParameter.NORTHING_AT_FALSE_ORIGIN));
    }

    @Override
    double coefD(final double coefU, final double coefV, final double coefT) {
        return coefD(coefU, coefV, coefT, alphac());
    }

    /**
     * <div class="fr">
     * Calcul du coefficient D pour des longitudes excédant l'intervalle -90;+90.
     * </div>
     * @param coefU
     * @param coefV
     * @param coefT
     * @param alphac
     * @return
     */
    public static double coefD(final double coefU, final double coefV, final double coefT, final double alphac) {
        return Math.atan2(Math.cos(coefU) * Math.sin(coefV) / Math.cos(coefT),
                (Math.cos(alphac) * Math.sin(coefT) - Math.sin(coefU)) / Math.sin(alphac) / Math.cos(coefT));
    }
}
