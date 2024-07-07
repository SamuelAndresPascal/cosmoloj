package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.Epsg1053;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::1055</div>
 * <div class="en">Time-dependent Position Vector transformation (geog3D domain)</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg1053
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1055 extends Abstract3DTo3D {

    public Epsg1055(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds,
            final double dtx, final double dty, final double dtz,
            final double drx, final double dry, final double drz,
            final double dds,
            final double t0) {
        super(source, target, new Epsg1053(tx, ty, tz, rx, ry, rz, ds, dtx, dty, dtz, drx, dry, drz, dds, t0));
    }

    public static Epsg1055 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        return new Epsg1055(source, target,
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.X_AXIS_ROTATION),
            (double) params.get(MethodParameter.Y_AXIS_ROTATION),
            (double) params.get(MethodParameter.Z_AXIS_ROTATION),
            (double) params.get(MethodParameter.SCALE_DIFFERENCE),
            (double) params.get(MethodParameter.RATE_OF_CHANGE_OF_X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.RATE_OF_CHANGE_OF_Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.RATE_OF_CHANGE_OF_Z_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.RATE_OF_CHANGE_OF_X_AXIS_ROTATION),
            (double) params.get(MethodParameter.RATE_OF_CHANGE_OF_Y_AXIS_ROTATION),
            (double) params.get(MethodParameter.RATE_OF_CHANGE_OF_Z_AXIS_ROTATION),
            (double) params.get(MethodParameter.RATE_OF_CHANGE_OF_SCALE_DIFFERENCE),
            (double) params.get(MethodParameter.PARAMETER_REFERENCE_EPOCH));
    }
}
