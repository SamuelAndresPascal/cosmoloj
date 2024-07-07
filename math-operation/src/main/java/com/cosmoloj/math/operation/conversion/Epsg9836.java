package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9836</div>
 * <div class="en">Geocentric/topocentric conversions</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg9602
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9836 implements CoordinateConversion, AutoInverse<double[]> {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    private final Ellipsoid ellipsoid;

    private final double[] x0;
    private final double[][] r;
    private final double[][] rt;

    public Epsg9836(final Ellipsoid ellipsoid, final double x0, final double y0, final double z0) {
        this.ellipsoid = ellipsoid;
        this.x0 = new double[]{x0, y0, z0};
        final Epsg9602 epsg9602 = new Epsg9602(ellipsoid);
        final double[] el = epsg9602.inverse(this.x0);
        this.r = new double[][]{
            {-Math.sin(el[LAMBDA]),                     Math.cos(el[LAMBDA]),                      0.},
            {-Math.sin(el[PHI]) * Math.cos(el[LAMBDA]), -Math.sin(el[PHI]) * Math.sin(el[LAMBDA]), Math.cos(el[PHI])},
            {Math.cos(el[PHI]) * Math.cos(el[LAMBDA]),  Math.cos(el[PHI]) * Math.sin(el[LAMBDA]),  Math.sin(el[PHI])}};
        this.rt = DoubleTabulars.transpose(r);
    }

    public static Epsg9836 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9836(ellipsoid,
            (double) params.get(MethodParameter.GEOCENTRIC_X_OF_TOPOCENTRIC_ORIGIN),
            (double) params.get(MethodParameter.GEOCENTRIC_Y_OF_TOPOCENTRIC_ORIGIN),
            (double) params.get(MethodParameter.GEOCENTRIC_Z_OF_TOPOCENTRIC_ORIGIN));
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return x0[ switch (parameter) {
            case GEOCENTRIC_X_OF_TOPOCENTRIC_ORIGIN -> X;
            case GEOCENTRIC_Y_OF_TOPOCENTRIC_ORIGIN -> Y;
            case GEOCENTRIC_Z_OF_TOPOCENTRIC_ORIGIN -> Z;
            default -> throw new IllegalArgumentException();
        } ];
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.GEOCENTRIC_X_OF_TOPOCENTRIC_ORIGIN,
                MethodParameter.GEOCENTRIC_Y_OF_TOPOCENTRIC_ORIGIN,
                MethodParameter.GEOCENTRIC_Z_OF_TOPOCENTRIC_ORIGIN);
    }

    @Override
    public double[] compute(final double[] input) {
        return DoubleTabulars.mult(this.r, DoubleTabulars.minus(input, this.x0));
    }

    @Override
    public double[] inverse(final double[] input) {
        return DoubleTabulars.add(DoubleTabulars.mult(this.rt, input), this.x0);
    }
}
