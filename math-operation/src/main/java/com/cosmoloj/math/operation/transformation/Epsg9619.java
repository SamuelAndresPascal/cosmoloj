package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9619</div>
 * <div class="en">Geographic Offsets (2D)</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9619 implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    private final double[] d;

    public Epsg9619(final double dphi, final double dlambda) {
        this.d = new double[]{dphi, dlambda};
    }

    public static Epsg9619 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg9619(
            (double) params.get(MethodParameter.LATITUDE_OFFSET),
            (double) params.get(MethodParameter.LONGITUDE_OFFSET));
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OFFSET -> d[0];
            case LONGITUDE_OFFSET -> d[1];
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OFFSET, MethodParameter.LONGITUDE_OFFSET);
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
