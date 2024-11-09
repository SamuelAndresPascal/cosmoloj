package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.Epsg1061;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::1063</div>
 * <div class="en">Molodensky-Badekas (PV geog2D domain)</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg1061
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1063 extends Abstract2DTo2D {

    public Epsg1063(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds,
            final double xp, final double yp, final double zp) {
        this(source, target, tx, ty, tz, rx, ry, rz, ds, xp, yp, zp, 0., 0.);
    }

    public Epsg1063(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds,
            final double epo1, final double epo2, final double epo3,
            final double inverseSourceHeight,
            final double inverseTargetHeight) {
        super(source, target, new Epsg1061(tx, ty, tz, rx, ry, rz, ds, epo1, epo2, epo3),
                inverseSourceHeight,
                inverseTargetHeight);
    }

    public static Epsg1063 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        return new Epsg1063(source, target,
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
