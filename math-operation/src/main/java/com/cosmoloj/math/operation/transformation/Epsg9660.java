package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9660</div>
 * <div class="en">Geographic Offsets (3D)</div>
 *
 * @author Samuel Andrés
 */
public class Epsg9660 implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    private final double[] d;

    public Epsg9660(final double dphi, final double dlambda, final double dh) {
        this.d = new double[]{dphi, dlambda, dh};
    }

    public static Epsg9660 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg9660(
            (double) params.get(MethodParameter.LATITUDE_OFFSET),
            (double) params.get(MethodParameter.LONGITUDE_OFFSET),
            (double) params.get(MethodParameter.VERTICAL_OFFSET));
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OFFSET -> d[0];
            case LONGITUDE_OFFSET -> d[1];
            case VERTICAL_OFFSET -> d[2];
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OFFSET,
                MethodParameter.LONGITUDE_OFFSET,
                MethodParameter.VERTICAL_OFFSET);
    }

    @Override
    public double[] compute(final double[] input) {
        return DoubleTabulars.add(input, d);
    }

    @Override
    public double[] inverse(final double[] input) {
        return DoubleTabulars.minus(input, d);
    }
}
