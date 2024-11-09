package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;

/**
 * <div>EPSG::9843</div>
 * <div class="en">Horizontal axis reversal – 2D CRS</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9843 implements CoordinateConversion, AutoInverse<double[]> {

    private static final int PHI_FORWARD = 0;
    private static final int LAMBDA_FORWARD = 1;
    private static final int LAMBDA_INVERSE = 0;
    private static final int PHI_INVERSE = 1;

    private final Ellipsoid ellipsoid;


    public Epsg9843(final Ellipsoid ellipsoid) {
        this.ellipsoid = ellipsoid;
    }

    public static Epsg9843 ofParams(final Ellipsoid ellipsoid) {
        return new Epsg9843(ellipsoid);
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        throw new IllegalArgumentException();
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of();
    }

    @Override
    public double[] compute(final double[] input) {
        return new double[]{input[LAMBDA_FORWARD], input[PHI_FORWARD]};
    }

    @Override
    public double[] inverse(final double[] input) {
        return new double[]{input[PHI_INVERSE], input[LAMBDA_INVERSE]};
    }
}
