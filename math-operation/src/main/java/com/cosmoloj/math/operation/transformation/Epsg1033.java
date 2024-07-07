package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::1033</div>
 * <div class="en">Position Vector transformation (geocentric domain)</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1033 extends AbstractHelmert {

    public Epsg1033(final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz, final double ds) {
        super(tx, ty, tz, rx, ry, rz, ds, false, false);
    }

    public static Epsg1033 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg1033(
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.X_AXIS_ROTATION),
            (double) params.get(MethodParameter.Y_AXIS_ROTATION),
            (double) params.get(MethodParameter.Z_AXIS_ROTATION),
            (double) params.get(MethodParameter.SCALE_DIFFERENCE));
    }
}
