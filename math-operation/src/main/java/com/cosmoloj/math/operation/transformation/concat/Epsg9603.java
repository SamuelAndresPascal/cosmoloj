package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.Epsg1031;
import com.cosmoloj.util.bib.Cite;
import java.util.Map;

/**
 * <div>EPSG::9603</div>
 * <div class="en">Geocentric Translations (geog2D domain)</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg1031
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9603 extends Abstract2DTo2D {

    public Epsg9603(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz) {
        this(source, target, tx, ty, tz, 0., 0.);
    }

    public Epsg9603(final Ellipsoid source, final Ellipsoid target,
            final double tx, final double ty, final double tz, final double inverseSourceHeight,
            final double inverseTargetHeight) {
        super(source, target, new Epsg1031(tx, ty, tz), inverseSourceHeight, inverseTargetHeight);
    }

    public static Epsg9603 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        return new Epsg9603(source, target,
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION));
    }
}
