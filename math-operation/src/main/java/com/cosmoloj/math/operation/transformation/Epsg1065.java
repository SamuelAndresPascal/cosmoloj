package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::1065</div>
 * <div class="en">Time-specific Position Vector transform (geocen)</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1065 extends AbstractTimeSpecificHelmert {

    public Epsg1065(final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds, final double t0) {
        super(new Epsg1033(tx, ty, tz, rx, ry, rz, ds), t0);
    }

    public static Epsg1065 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg1065(
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
