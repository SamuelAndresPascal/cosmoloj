package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.util.complex.Complex;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9811</div>
 * <div class="en">New Zealand Map Grid</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9811 implements InvertibleProjection {

    protected static final int PHI = 0;
    protected static final int LAMBDA = 1;
    protected static final int EASTING = 0;
    protected static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private static final Complex Z0 = Complex.of(6023150., 2510000.);
    private static final Complex COEF_B1 = Complex.real(0.7557853228);
    private static final Complex COEF_B2 = Complex.of(0.249204646, 0.003371507);
    private static final Complex COEF_B3 = Complex.of(-0.001541739, 0.041058560);
    private static final Complex COEF_B4 = Complex.of(-0.10162907, 0.01727609);
    private static final Complex COEF_B5 = Complex.of(-0.26623489, -0.36249218);
    private static final Complex COEF_B6 = Complex.of(-0.6870983, -1.1651967);
    private static final Complex B1 = Complex.real(1.3231270439);
    private static final Complex B2 = Complex.of(-0.577245789, -0.007809598);
    private static final Complex B3 = Complex.of(0.508307513, -0.112208952);
    private static final Complex B4 = Complex.of(-0.15094762, 0.18200602);
    private static final Complex B5 = Complex.of(1.01418179, 1.64497696);
    private static final Complex B6 = Complex.of(1.9660549, 2.5127645);

    public Epsg9811(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;
    }

    public static Epsg9811 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9811(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_NATURAL_ORIGIN -> phi0;
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
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];

        final double dphi = phi - Math.toRadians(41.);
        final double dpsi = dpsi(dphi);
        final double dlambda = lambda - Math.toRadians(173.);

        final Complex dzeta = Complex.of(dpsi, dlambda);

        final Complex z = dzeta.mult(ellipsoid.a())
                .mult(COEF_B1.add(dzeta
                        .mult(COEF_B2.add(dzeta
                                .mult(COEF_B3.add(dzeta
                                        .mult(COEF_B4.add(dzeta
                                                .mult(COEF_B5.add(dzeta
                                                        .mult(COEF_B6)
                                                ))
                                        ))
                                ))
                        ))
                ));

        final Complex p = z.add(Z0.opposite());
        return new double[]{p.getImaginary(), p.getReal()};
    }

    @Override
    public double[] inverse(final double[] input) {
        final double northing = input[NORTHING];
        final double easting = input[EASTING];

        final Complex z = Complex.of(northing, easting).add(Z0.opposite()).div(ellipsoid.a());

        final Complex dzeta = dzeta(z);

        final double dlambda = dzeta.getImaginary();
        final double dpsi = dzeta.getReal();
        final double dphi = dphi(dpsi);

        return new double[]{dphi + Math.toRadians(41.), dlambda + Math.toRadians(173.)};
    }

    Complex dzeta(final Complex z) {
        final Complex dzeta0 = z.mult(B1.add(z.mult(B2.add(
                z.mult(B3.add(z.mult(B4.add(z.mult(B5.add(z.mult(B6)))))))))));

        Complex dzeta = dzeta(z, dzeta0);

        while (true) {
            final Complex tmp = dzeta(z, dzeta);
            if (tmp.add(dzeta.opposite()).norm() < 1e-11) {
                return tmp;
            } else {
                dzeta = tmp;
            }
        }
    }

    Complex dzeta(final Complex z, final Complex dzeta) {
        final Complex numerator = z.add(dzeta.mult(dzeta)
                .mult(COEF_B2.add(dzeta
                        .mult(COEF_B3.mult(2.).add(dzeta
                                .mult(COEF_B4.mult(3.).add(dzeta
                                        .mult(COEF_B5.mult(4.).add(dzeta
                                                .mult(COEF_B6.mult(5.))
                                        ))
                                ))
                        ))
                )));
        final Complex denominator = COEF_B1.add(dzeta
                        .mult(COEF_B2.mult(2.).add(dzeta
                                .mult(COEF_B3.mult(3.).add(dzeta
                                        .mult(COEF_B4.mult(4.).add(dzeta
                                                .mult(COEF_B5.mult(5.).add(dzeta
                                                        .mult(COEF_B6.mult(6.))
                                                ))
                                        ))
                                ))
                        ))
                );
        return numerator.div(denominator);
    }

    double dpsi(final double dphi) {
        final double dfi = dphi * 1e-5;
        return dfi * (0.6399275073
                + dfi * (-0.1358797613
                + dfi * (0.063294409
                + dfi * (-0.02526853
                + dfi * (0.0117879
                + dfi * (-0.0055161
                + dfi * (0.0026906
                + dfi * (-0.001333
                + dfi * (0.00067
                - dfi * 0.00034)))))))));
    }

    double dphi(final double dpsi) {
        return 1e5 * dpsi * (1.5627014243
                + dpsi * (0.5185406398
                + dpsi * (-0.03333098
                + dpsi * (-0.1052906
                + dpsi * (-0.0368594
                + dpsi * (0.00731
                + dpsi * (0.01220
                + dpsi * (0.00394
                - dpsi * 0.0013))))))));
    }
}
