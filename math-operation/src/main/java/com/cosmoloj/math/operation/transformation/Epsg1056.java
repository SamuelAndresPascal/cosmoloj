package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::1056</div>
 * <div class="en">Time-dependent Coordinate Frame rotation (geocentric domain)</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1056 extends Epsg1053 {

    public Epsg1056(final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds,
            final double dtx, final double dty, final double dtz,
            final double drx, final double dry, final double drz,
            final double dds, final double t0) {
        super(tx, ty, tz, -rx, -ry, -rz, ds, dtx, dty, dtz, -drx, -dry, -drz, dds, t0);
    }

    public static Epsg1056 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg1056(
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
