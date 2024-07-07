package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::1066</div>
 * <div class="en">Time-specific Coordinate Frame rotation (geocen)</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1066 extends AbstractTimeSpecificHelmert {

    public Epsg1066(final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds, final double t0) {
        super(new Epsg1032(tx, ty, tz, rx, ry, rz, ds), t0);
    }

    public static Epsg1066 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg1066(
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.X_AXIS_ROTATION),
            (double) params.get(MethodParameter.Y_AXIS_ROTATION),
            (double) params.get(MethodParameter.Z_AXIS_ROTATION),
            (double) params.get(MethodParameter.SCALE_DIFFERENCE),
            (double) params.get(MethodParameter.TRANSFORMATION_REFERENCE_EPOCH));
    }
}
