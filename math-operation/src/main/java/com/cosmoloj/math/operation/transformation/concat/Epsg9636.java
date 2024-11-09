package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.Epsg1034;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::9636</div>
 * <div class="en">Molodensky-Badekas (CF geog2D domain)</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg1034
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9636 extends Abstract2DTo2D {

    public Epsg9636(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds,
            final double xp, final double yp, final double zp) {
        this(source, target, tx, ty, tz, rx, ry, rz, ds, xp, yp, zp, 0., 0.);
    }

    public Epsg9636(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds,
            final double epo1, final double epo2, final double epo3,
            final double inverseSourceHeight,
            final double inverseTargetHeight) {
        super(source, target, new Epsg1034(tx, ty, tz, rx, ry, rz, ds, epo1, epo2, epo3),
                inverseSourceHeight,
                inverseTargetHeight);
    }

    public static Epsg9636 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        return new Epsg9636(source, target,
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
