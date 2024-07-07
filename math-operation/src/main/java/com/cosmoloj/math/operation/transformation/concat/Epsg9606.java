package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.Epsg1033;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::9606</div>
 * <div class="en">Position Vector transformation (geog2D domain)</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg1033
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9606 extends Abstract2DTo2D {

    public Epsg9606(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds) {
        this(source, target, tx, ty, tz, rx, ry, rz, ds, 0., 0.);
    }

    public Epsg9606(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds,
            final double inverseSourceHeight,
            final double inverseTargetHeight) {
        super(source, target, new Epsg1033(tx, ty, tz, rx, ry, rz, ds), inverseSourceHeight, inverseTargetHeight);
    }

    public static Epsg9606 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        return new Epsg9606(source, target,
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.X_AXIS_ROTATION),
            (double) params.get(MethodParameter.Y_AXIS_ROTATION),
            (double) params.get(MethodParameter.Z_AXIS_ROTATION),
            (double) params.get(MethodParameter.SCALE_DIFFERENCE));
    }
}
