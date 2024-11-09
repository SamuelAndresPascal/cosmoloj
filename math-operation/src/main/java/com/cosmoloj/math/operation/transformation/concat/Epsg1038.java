package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.Epsg1032;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::1038</div>
 * <div class="en">Coordinate Frame rotation (geog3D domain)</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg1032
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1038 extends Abstract3DTo3D {

    public Epsg1038(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz,
            final double rx, final double ry, final double rz,
            final double ds) {
        super(source, target, new Epsg1032(tx, ty, tz, rx, ry, rz, ds));
    }

    public static Epsg1038 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        return new Epsg1038(source, target,
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.X_AXIS_ROTATION),
            (double) params.get(MethodParameter.Y_AXIS_ROTATION),
            (double) params.get(MethodParameter.Z_AXIS_ROTATION),
            (double) params.get(MethodParameter.SCALE_DIFFERENCE));
    }
}
