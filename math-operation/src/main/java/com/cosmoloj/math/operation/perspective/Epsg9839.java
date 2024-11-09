package com.cosmoloj.math.operation.perspective;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9839</div>
 * <div class="en">Vertical Perspective (orthographic case)</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9839 extends AbstractPerspective {

    private static final int U = 0;
    private static final int V = 1;

    private final double phi0;
    private final double lambda0;

    public Epsg9839(final Ellipsoid ellipsoid, final double phi0, final double lambda0) {
        // h0 set to 0. but the value does not matter (see documentation in IOGP Guidance Note number 7 part 2 EPSG:9840
        super(ellipsoid, phi0, lambda0, 0.);
        this.phi0 = phi0;
        this.lambda0 = lambda0;
    }

    public static Epsg9839 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9839(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_TOPOCENTRIC_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_TOPOCENTRIC_ORIGIN));
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_TOPOCENTRIC_ORIGIN -> phi0;
            case LONGITUDE_OF_TOPOCENTRIC_ORIGIN -> lambda0;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_TOPOCENTRIC_ORIGIN,
                MethodParameter.LONGITUDE_OF_TOPOCENTRIC_ORIGIN);
    }

    @Override
    public double[] perspective(final double[] topocentric) {
        return new double[]{
            topocentric[U],
            topocentric[V]
        };
    }
}
