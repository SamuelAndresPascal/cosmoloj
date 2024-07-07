package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.util.bib.Reference;
import java.util.Map;

/**
 * <div>EPSG::1061</div>
 * <div class="en">Molodensky-Badekas transformation (PV geocentric domain)</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1061 extends AbstractMolodenskyBadekas {

    public Epsg1061(final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz, final double ds,
            final double epo1, final double epo2, final double epo3) {
        super(tx, ty, tz, rx, ry, rz, ds, epo1, epo2, epo3, false, false);
    }

    public static Epsg1061 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg1061(
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
