package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.math.operation.surface.Surface;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1024</div>
 * <div class="en">Popular Visualisation Pseudo-Mercator ("Web Mercator")</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1024 implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Surface ellipsoid;
    private final double a;
    private final double lambda0;
    private final double fe;
    private final double fn;

    public Epsg1024(final Ellipsoid ellipsoid, final double lambda0, final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;
    }

    public Epsg1024(final Spheroid ellipsoid, final double lambda0, final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.r();
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;
    }

    public static Epsg1024 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1024(ellipsoid,
                (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
                (double) params.get(MethodParameter.FALSE_EASTING),
                (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    public static Epsg1024 ofParams(final Spheroid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1024(ellipsoid,
                (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
                (double) params.get(MethodParameter.FALSE_EASTING),
                (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public Surface getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_NATURAL_ORIGIN -> 0.;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public double[] compute(final double[] input) {
        return new double[]{
            fe + a * (input[LAMBDA] - lambda0),
            fn + a * Math.log(Math.tan(Math.PI / 4. + input[PHI] / 2.))};
    }

    @Override
    public double[] inverse(final double[] input) {
        return new double[]{
            Math.PI / 2. - 2. * Math.atan(Math.exp((fn - input[NORTHING]) / a)),
            (input[EASTING] - fe) / a + lambda0};
    }
}
