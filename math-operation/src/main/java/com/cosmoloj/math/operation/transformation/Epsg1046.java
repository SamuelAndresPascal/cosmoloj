package com.cosmoloj.math.operation.transformation;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1046</div>
 * <div class="en">Vertical Offset and Slope</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1046 implements OffsetTransformation<double[]>, AutoInverse<double[]> {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int X = 2;

    private final double nu0;
    private final double rho0;
    private final double a;
    private final double iphi;
    private final double ilambda;
    private final double phi0;
    private final double lambda0;

    public Epsg1046(final Ellipsoid ellipsoid, final double phi0, final double lambda0,
            final double a, final double iphi, final double ilambda, final String horizontalCrsCode) {
        this.nu0 = ellipsoid.nu(phi0);
        this.rho0 = ellipsoid.rho(phi0);
        this.a = a;
        this.iphi = iphi;
        this.ilambda = ilambda;
        this.phi0 = phi0;
        this.lambda0 = lambda0;
    }

    public static Epsg1046 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1046(ellipsoid,
            (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT),
            (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT),
            (double) params.get(MethodParameter.VERTICAL_OFFSET),
            (double) params.get(MethodParameter.INCLINATION_IN_LATITUDE),
            (double) params.get(MethodParameter.INCLINATION_IN_LONGITUDE),
            Double.toString((double) params.get(MethodParameter.HORIZONTAL_CRS_CODE))); // 4258
    }

    @Override
    public double[] compute(final double[] input) {
        return new double[]{
            input[PHI],
            input[LAMBDA],
            input[X] + (a + (iphi *  rho0 * (input[PHI] - phi0))
                + (ilambda * nu0 * (input[LAMBDA] - lambda0) * Math.cos(input[PHI])))
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        return new double[]{
            input[PHI],
            input[LAMBDA],
            input[X] - (a + (iphi *  rho0 * (input[PHI] - phi0))
                + (ilambda * nu0 * (input[LAMBDA] - lambda0) * Math.cos(input[PHI])))
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
