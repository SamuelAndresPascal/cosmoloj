package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1031</div>
 * <div class="en">3-parameter geocentric translations</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1031 implements InversibleCoordinateTransformation, AutoInverse<double[]> {

    private final double[] t;

    public Epsg1031(final double xt, final double yt, final double zt) {
        this.t = new double[]{xt, yt, zt};
    }

    public static Epsg1031 ofParams(final Map<MethodParameter, ?> params) {
        return new Epsg1031(
            (double) params.get(MethodParameter.X_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Y_AXIS_TRANSLATION),
            (double) params.get(MethodParameter.Z_AXIS_TRANSLATION));
    }

    @Override
    public double[] compute(final double[] input) {
        return DoubleTabulars.add(input, t);
    }

    @Override
    public double[] inverse(final double[] input) {
        return DoubleTabulars.minus(input, t);
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.X_AXIS_TRANSLATION,
                MethodParameter.Y_AXIS_TRANSLATION,
                MethodParameter.Z_AXIS_TRANSLATION);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return t[ switch (parameter) {
            case X_AXIS_TRANSLATION -> 0;
            case Y_AXIS_TRANSLATION -> 1;
            case Z_AXIS_TRANSLATION -> 2;
            default -> throw new IllegalArgumentException();
        } ];
    }
}
