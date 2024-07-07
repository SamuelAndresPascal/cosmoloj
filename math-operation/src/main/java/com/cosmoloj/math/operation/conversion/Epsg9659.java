package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;

/**
 * <div>EPSG::9659</div>
 * <div class="en">Geographic 3D to 2D conversions</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9659 implements CoordinateConversion, AutoInverse<double[]> {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;

    private final Ellipsoid ellipsoid;
    private final double inverseHeight;

    public Epsg9659(final Ellipsoid ellipsoid, final double inverseHeight) {
        this.ellipsoid = ellipsoid;
        this.inverseHeight = inverseHeight;
    }

    public Epsg9659(final Ellipsoid ellipsoid) {
        this(ellipsoid, 0.);
    }

    public static Epsg9659 ofParams(final Ellipsoid ellipsoid) {
        return new Epsg9659(ellipsoid);
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
        return new double[]{input[PHI], input[LAMBDA]};
    }

    @Override
    public double[] inverse(final double[] input) {
        return new double[]{input[PHI], input[LAMBDA], inverseHeight};
    }
}
