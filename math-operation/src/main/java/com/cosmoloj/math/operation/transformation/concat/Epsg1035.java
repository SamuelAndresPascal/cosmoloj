package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.Epsg1031;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::1035</div>
 * <div class="en">Geocentric Translations (geog3D domain)</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg1031
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1035 extends Abstract3DTo3D {

    public Epsg1035(final Ellipsoid source, final Ellipsoid target,
            final double xt, final double yt, final double zt) {
        super(source, target, new Epsg1031(xt, yt, zt));
    }

    public static Epsg1035 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        return new Epsg1035(source, target,
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION));
    }
}
