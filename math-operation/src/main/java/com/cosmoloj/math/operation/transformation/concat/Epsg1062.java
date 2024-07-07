package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.Epsg1061;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::1062</div>
 * <div class="en">Molodensky-Badekas (PV geog3D domain)</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg1061
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1062 extends Abstract3DTo3D {

    public Epsg1062(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds,
            final double epo1, final double epo2, final double epo3) {
        super(source, target, new Epsg1061(tx, ty, tz, rx, ry, rz, ds, epo1, epo2, epo3));
    }

    public static Epsg1062 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        return new Epsg1062(source, target,
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.X_AXIS_ROTATION),
            (double) params.get(MethodParameter.Y_AXIS_ROTATION),
            (double) params.get(MethodParameter.Z_AXIS_ROTATION),
            (double) params.get(MethodParameter.SCALE_DIFFERENCE),
            (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT),
            (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT),
            (double) params.get(MethodParameter.ORDINATE_3_OF_EVALUATION_POINT));
    }
}
