package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.util.MathUtil;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9807</div>
 * <div class="en">Transverse Mercator</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9807jhs implements InversibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double f;
    private final double e;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;
    private final double k0;

    private final double n;
    private final double k0coefB;
    private final double h1;
    private final double h2;
    private final double h3;
    private final double h4;
    private final double h1p;
    private final double h2p;
    private final double h3p;
    private final double h4p;
    private final double ksio;

    private final double precision = 1e-9;

    public Epsg9807jhs(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double k0,
            final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.f = ellipsoid.f();
        this.e = ellipsoid.e();
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;
        this.k0 = k0;

        this.n = f / (2. - f);
        final double n2 = n * n;
        this.k0coefB = k0 * a / (1. + n) * (1. + (n2 * (1. / 4. + n2 / 64.)));

        this.h1 = n * (1. / 2. + n * (-2. / 3. + n * (5. / 16. + n * 41. / 180.)));
        this.h2 = n2 * (13. / 48. + n * (-3. / 5. + n * 557. / 1440.));
        this.h3 = n * n2 * (61. / 240. - n * 103. / 140.);
        this.h4 = n2 * n2 * 49561. / 161280.;

        this.h1p = n * (1. / 2. + n * (-2. / 3. + n * (37. / 96. - n * 1. / 360.)));
        this.h2p = n2 * (1. / 48. + n * (1. / 15. - n * 437. / 1440.));
        this.h3p = n * n2 * (17. / 480. - n * 37. / 840.);
        this.h4p = n2 * n2 * 4397. / 161280.;

        if (Math.abs(phi0) < precision) {
            this.ksio = 0.;
        } else if (Math.abs(phi0 - Math.PI / 2.) < precision) {
            this.ksio = Math.PI / 2.;
        } else if (Math.abs(phi0 + Math.PI / 2.) < precision) {
            this.ksio = -Math.PI / 2.;
        } else {

            final double coefQo = coefQ(phi0);
            final double betao = beta(coefQo);
            final double ksio0 = Math.asin(Math.sin(betao));

            final double ksio1 = h1 * Math.sin(2. * ksio0);
            final double ksio2 = h2 * Math.sin(4. * ksio0);
            final double ksio3 = h3 * Math.sin(6. * ksio0);
            final double ksio4 = h4 * Math.sin(8. * ksio0);
            this.ksio = ksio0 + ksio1 + ksio2 + ksio3 + ksio4;
        }
    }

    public static Epsg9807jhs ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9807jhs(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN),
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
            case SCALE_FACTOR_AT_NATURAL_ORIGIN -> k0;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public double[] compute(final double[] input) {
        final double coefQ = coefQ(input[PHI]);
        final double beta = beta(coefQ);
        final double eta0 = MathUtil.atanh(Math.cos(beta) * Math.sin(input[LAMBDA] - lambda0));
        final double ksi0 = Math.asin(Math.sin(beta) * Math.cosh(eta0));

        final double ksi1 = h1 * Math.sin(2. * ksi0) * Math.cosh(2. * eta0);
        final double ksi2 = h2 * Math.sin(4. * ksi0) * Math.cosh(4. * eta0);
        final double ksi3 = h3 * Math.sin(6. * ksi0) * Math.cosh(6. * eta0);
        final double ksi4 = h4 * Math.sin(8. * ksi0) * Math.cosh(8. * eta0);
        final double ksi = ksi0 + ksi1 + ksi2 + ksi3 + ksi4;

        final double eta1 = h1 * Math.cos(2. * ksi0) * Math.sinh(2. * eta0);
        final double eta2 = h2 * Math.cos(4. * ksi0) * Math.sinh(4. * eta0);
        final double eta3 = h3 * Math.cos(6. * ksi0) * Math.sinh(6. * eta0);
        final double eta4 = h4 * Math.cos(8. * ksi0) * Math.sinh(8. * eta0);
        final double eta = eta0 + eta1 + eta2 + eta3 + eta4;

        return new double[]{fe + k0coefB * eta, fn + k0coefB * (ksi - ksio)};
    }

    @Override
    public double[] inverse(final double[] input) {

        final double eta = (input[EASTING] - fe) / k0coefB;
        final double ksi = ((input[NORTHING] - fn) + k0coefB * ksio) / k0coefB;

        final double ksi1 = h1p * Math.sin(2. * ksi) * Math.cosh(2. * eta);
        final double ksi2 = h2p * Math.sin(4. * ksi) * Math.cosh(4. * eta);
        final double ksi3 = h3p * Math.sin(6. * ksi) * Math.cosh(6. * eta);
        final double ksi4 = h4p * Math.sin(8. * ksi) * Math.cosh(8. * eta);
        final double ksi0 = ksi - (ksi1 + ksi2 + ksi3 + ksi4);

        final double eta1 = h1p * Math.cos(2. * ksi) * Math.sinh(2. * eta);
        final double eta2 = h2p * Math.cos(4. * ksi) * Math.sinh(4. * eta);
        final double eta3 = h3p * Math.cos(6. * ksi) * Math.sinh(6. * eta);
        final double eta4 = h4p * Math.cos(8. * ksi) * Math.sinh(8. * eta);
        final double eta0 = eta - (eta1 + eta2 + eta3 + eta4);

        final double beta = Math.asin(Math.sin(ksi0) / Math.cosh(eta0));
        final double coefQp = MathUtil.asinh(Math.tan(beta));
        final double coefQpp = invCoefQpp(coefQp);

        return new double[]{Math.atan(Math.sinh(coefQpp)), lambda0 + Math.asin(Math.tanh(eta0) / Math.cos(beta))};
    }

    final double invCoefQpp(final double coefQp) {
        double coefQpp = coefQp;

        while (true) {
            final double tmp = coefQp + (e * MathUtil.atanh(e * Math.tanh(coefQpp)));
            if (Math.abs(tmp - coefQpp) < precision) {
                return tmp;
            } else {
                coefQpp = tmp;
            }
        }
    }

    final double coefQ(final double phi) {
        return  MathUtil.asinh(Math.tan(phi)) - e * MathUtil.atanh(e * Math.sin(phi));
    }

    final double beta(final double coefQ) {
        return Math.atan(Math.sinh(coefQ));
    }
}
