package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::1032</div>
 * <div class="en">Coordinate Frame rotation (geocentric domain)</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1032 extends Epsg1033 {

    public Epsg1032(final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz, final double ds) {
        super(tx, ty, tz, -rx, -ry, -rz, ds);
    }

    public static Epsg1032 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg1032(
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.X_AXIS_ROTATION),
            (double) params.get(MethodParameter.Y_AXIS_ROTATION),
            (double) params.get(MethodParameter.Z_AXIS_ROTATION),
            (double) params.get(MethodParameter.SCALE_DIFFERENCE));
    }
}
