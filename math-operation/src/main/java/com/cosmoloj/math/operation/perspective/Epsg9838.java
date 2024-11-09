package com.cosmoloj.math.operation.perspective;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9838</div>
 * <div class="en">Vertical Perspective</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9838 extends AbstractPerspective {

    private static final int U = 0;
    private static final int V = 1;
    private static final int W = 2;

    private final double phi0;
    private final double lambda0;
    private final double h0;
    private final double hnu;

    public Epsg9838(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double h0,
            final double hnu) {
        super(ellipsoid, phi0, lambda0, h0);
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.h0 = h0;
        this.hnu = hnu;
    }

    public static Epsg9838 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9838(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_TOPOCENTRIC_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_TOPOCENTRIC_ORIGIN),
            (double) params.get(MethodParameter.ELLIPSOIDAL_HEIGHT_OF_TOPOCENTRIC_ORIGIN),
            (double) params.get(MethodParameter.VIEWPOINT_HEIGHT));
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_TOPOCENTRIC_ORIGIN -> phi0;
            case LONGITUDE_OF_TOPOCENTRIC_ORIGIN -> lambda0;
            case ELLIPSOIDAL_HEIGHT_OF_TOPOCENTRIC_ORIGIN -> h0;
            case VIEWPOINT_HEIGHT -> hnu;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_TOPOCENTRIC_ORIGIN,
                MethodParameter.LONGITUDE_OF_TOPOCENTRIC_ORIGIN,
                MethodParameter.ELLIPSOIDAL_HEIGHT_OF_TOPOCENTRIC_ORIGIN,
                MethodParameter.VIEWPOINT_HEIGHT);
    }

    @Override
    public double[] perspective(final double[] topocentric) {
        final double cte = hnu / (hnu - topocentric[W]);
        return new double[]{
            topocentric[U] * cte,
            topocentric[V] * cte
        };
    }
}
