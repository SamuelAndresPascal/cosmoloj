package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.trials.AbstractInitTrials;
import com.cosmoloj.math.util.integral.Integral;
import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import java.util.List;
import java.util.function.DoubleUnaryOperator;
import com.cosmoloj.math.operation.trials.AbsoluteDifferenceDoubleTrials;

/**
 * <div>SNYDER::10</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.MAP_PROJECTIONS)
@SectionReference(type = SectionReferenceType.SECTION, number = 10)
@Page(81)
public abstract class CylindricalEqualAreaEllipsoid implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double lambda0;
    private final double a;

    protected CylindricalEqualAreaEllipsoid(final Ellipsoid ellipsoid, final double lambda0) {
        this.ellipsoid = ellipsoid;
        this.lambda0 = lambda0;
        this.a = ellipsoid.a();
    }

    protected final double lambda0() {
        return lambda0;
    }

    protected final double a() {
        return a;
    }

    public static class Normal extends CylindricalEqualAreaEllipsoid {

        private final double phis;
        private final double cosphis;
        private final double k0;

        public Normal(final Ellipsoid ellipsoid, final double phis, final double lambda0) {
            super(ellipsoid, lambda0);
            this.phis = phis;
            this.cosphis = Math.cos(phis);
            this.k0 = cosphis / ellipsoid.eSinSqrt(phis);
        }

        @Override
        public double[] compute(final double[] input) {

            return new double[]{a() * k0 * (input[LAMBDA] - lambda0()),
                a() * getSurface().q(input[PHI]) / (2. * k0)};
        }

        @Override
        public Object getParameter(final MethodParameter parameter) {
            return switch (parameter) {
                    case LATITUDE_OF_1ST_STANDARD_PARALLEL -> phis;
                    case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0();
                    default -> throw new IllegalArgumentException();
                };
        }

        @Override
        public List<MethodParameter> getParameters() {
            return List.of(MethodParameter.LATITUDE_OF_STANDARD_PARALLEL,
                    MethodParameter.LONGITUDE_OF_ORIGIN);
        }

        @Override
        public double[] inverse(final double[] input) {
            return new double[]{Math.asin(input[NORTHING] / a() * cosphis), lambda0() + input[EASTING] / (a() * k0)};
        }
    }

    private abstract static class H0 extends CylindricalEqualAreaEllipsoid {

        private final double h0;
        private final double qp;

        protected H0(final Ellipsoid ellipsoid, final double h0, final double lambda0) {
            super(ellipsoid, lambda0);
            this.h0 = h0;
            this.qp = ellipsoid.qp();
        }

        protected final double h0() {
            return h0;
        }

        protected final double qp() {
            return qp;
        }

        static double q(final Ellipsoid ellipsoid, final double beta) {
            return ellipsoid.qp() * Math.sin(beta);
        }

        double q(final double beta) {
            return qp * Math.sin(beta);
        }

        static double phic(final Ellipsoid ellipsoid, final double q) {
            return ellipsoid.new PhiIterator(1e-9, q).loop();
        }

        double phic(final double q) {
            return phic(this.getSurface(), q);
        }
    }

    public static class Transverse extends H0 {
        private final double phi0;
        private final double m0;

        public Transverse(final Ellipsoid ellipsoid, final double h0, final double phi0, final double lambda0) {
            super(ellipsoid, h0, lambda0);
            this.phi0 = phi0;
            this.m0 = ellipsoid.m(phi0);
        }

        @Override
        public double[] compute(final double[] input) {
            final double l = input[LAMBDA] - lambda0();

            final double beta = getSurface().betaPhi(input[PHI]);
            final double betac = Math.atan2(Math.tan(beta), Math.cos(l));
            final double qc = q(betac);

            // si betac = +/-90°, phic = betac
            final double phic = phic(qc);

            return new double[]{
                a() * Math.cos(beta) * Math.cos(phic) * Math.sin(l)
                    / (h0() * Math.cos(betac) * getSurface().eSinSqrt(phic)),
                h0() * (getSurface().m(phic) - m0)
            };
        }

        @Override
        public double[] inverse(final double[] input) {

            final double phic = getSurface().phi1(getSurface().mu(m0 + input[NORTHING] / h0()));
            final double betac = getSurface().betaPhi(phic);
            final double cosbetac = Math.cos(betac);
            final double bp = -Math.asin(h0() * input[EASTING] * cosbetac * getSurface().eSinSqrt(phic)
                    / a() / Math.cos(phic));
            final double q = q(Math.asin(Math.cos(bp) * Math.sin(betac)));

            return new double[]{
                phic(q),
                lambda0() - Math.atan(Math.tan(bp / cosbetac))
            };
        }

        @Override
        public Object getParameter(final MethodParameter parameter) {
            return switch (parameter) {
                case LATITUDE_OF_1ST_STANDARD_PARALLEL -> phi0;
                // h0
                case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0();
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public List<MethodParameter> getParameters() {
            return List.of(MethodParameter.LATITUDE_OF_STANDARD_PARALLEL,
                    MethodParameter.LONGITUDE_OF_ORIGIN);
        }
    }

    public static class Oblique extends H0 {

        private final double phip;
        private final double lambdap;
        private final double betap;
        private final double sinbetap;
        private final double cosbetap;
        private final double coefB;
        private final double coefA2;
        private final double coefA4;

        public Oblique(final Ellipsoid ellipsoid, final double h0, final double phip, final double lambdap) {
            super(ellipsoid, h0, lambdap + Math.PI / 2.);
            this.phip = phip;
            this.lambdap = lambdap;
            this.betap = ellipsoid.betaPhi(phip);
            this.sinbetap = Math.sin(betap);
            this.cosbetap = Math.cos(betap);
            final double[] poleCoefs = poleCoefs();
            this.coefB = poleCoefs[0];
            this.coefA2 = poleCoefs[1];
            this.coefA4 = poleCoefs[2];
        }

        @Override
        public double[] compute(final double[] input) {
            final double l = input[LAMBDA] - lambdap;

            final double beta = getSurface().betaPhi(input[PHI]);
            final double sinbeta = Math.sin(beta);
            final double cosbeta = Math.cos(beta);
            final double cosl = Math.cos(l);

            final double lp = Math.atan2(cosbetap * sinbeta - sinbetap * cosbeta * cosl,
                    cosbeta * Math.sin(l));

            return new double[]{
                a() * h0() * (coefB * lp + coefA2 * Math.sin(2. * lp) + coefA4 * Math.sin(4. * lp)),
                a() * qp() / 2. * (sinbetap * sinbeta + cosbetap * cosbeta * cosl) / (h0() * f(lp))
            };
        }

        @Override
        public double[] inverse(final double[] input) {

            final double lp = new LpTrials(input[EASTING]).loop();
            final double sinlp = Math.sin(lp);
            final double sinbp = 2. * f(lp) * h0() * input[NORTHING] / (a()  * qp());
            final double cosbp = Math.cos(Math.asin(sinbp));

            final double q = qp() * (sinbetap * sinbp + cosbetap * cosbp * sinlp);

            return new double[]{
                getSurface().new PhiIterator(1e-9, q).loop(),
                lambdap + Math.atan2(cosbp * Math.cos(lp), cosbetap * sinbp - sinbetap * cosbp * sinlp)
            };
        }

        double f(final double lambda) {
            return coefB + 2. * coefA2 * Math.cos(2. * lambda) + 4. * coefA4 * Math.cos(4. * lambda);
        }

        static double coefF(final Ellipsoid ellipsoid, final double phip, final double lambdap) {
            final double betap = ellipsoid.betaPhi(phip);
            final double betac = Math.asin(Math.cos(betap) * Math.sin(lambdap));
            final double phic = phic(ellipsoid, q(ellipsoid, betac));
            final double qp = ellipsoid.qp();
            final double sinbetap = Math.sin(betap);
            final double cosbetap = Math.cos(betap);
            final double sinphic = Math.sin(phic);
            final double sinphic2 = sinphic * sinphic;
            final double cosphic = Math.cos(phic);
            final double cosphic2 = cosphic * cosphic;
            final double cosbetac = Math.cos(betac);
            final double coslambdap = Math.cos(lambdap);

            return Math.sqrt(
                    sinbetap * sinbetap * cosphic2
                                    / ((1. - ellipsoid.e2() * sinphic2) * cosbetac * cosbetac * cosbetac * cosbetac)
                            + (1. - ellipsoid.e2() * sinphic2) * qp * qp * cosbetap * cosbetap * coslambdap * coslambdap
                                    / (4. * cosphic2));
        }

        public static DoubleUnaryOperator coefB(final Ellipsoid ellipsoid) {
            return phip -> Integral.sum(l -> coefF(ellipsoid, phip, l), 0., Math.PI / 2., 10) * 2. / Math.PI;
        }

        public static DoubleUnaryOperator coefAn(final Ellipsoid ellipsoid, final int n) {
            return phip -> Integral.sum(l -> coefF(ellipsoid, phip, l) * Math.cos(n * l), 0., Math.PI / 2., 10)
                    * 4. / Math.PI / n;
        }

        public static double b(final Ellipsoid ellipsoid) {

            return Integral.sum(phip -> coefB(ellipsoid).applyAsDouble(phip), 0., Math.PI / 2., 10) * 2. / Math.PI;
        }

        public static double an(final Ellipsoid ellipsoid, final int n) {

            return Integral.sum(phip -> coefB(ellipsoid).applyAsDouble(phip) * Math.cos(n * phip), 0., Math.PI / 2., 10)
                    * 4. / Math.PI;
        }

        public static double bn(final Ellipsoid ellipsoid, final int n) {

            return Integral.sum(phip -> coefAn(ellipsoid, n).applyAsDouble(phip), 0., Math.PI / 2., 10) * 2. / Math.PI;
        }

        public static double anm(final Ellipsoid ellipsoid, final int n, final int m) {

            return Integral.sum(phip -> coefAn(ellipsoid, n).applyAsDouble(phip) * Math.cos(m * phip),
                    0., Math.PI / 2., 10) * 4. / Math.PI;
        }

        @Override
        public Object getParameter(final MethodParameter parameter) {
            return switch (parameter) {
                // case -> phip;
                // h0
                case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0();
                default -> throw new IllegalArgumentException();
            };
        }

        @Override
        public List<MethodParameter> getParameters() {
            return List.of(MethodParameter.LATITUDE_OF_STANDARD_PARALLEL,
                    MethodParameter.LONGITUDE_OF_ORIGIN);
        }

        final double[] poleCoefs() {
            final double[] line;
            final Ellipsoid ellipsoid = getSurface();
            if (Ellipsoid.CLARKE_1866 == ellipsoid) {
                line = GENERAL[0];
            } else if (Ellipsoid.INTERNATIONAL == ellipsoid) {
                line = GENERAL[1];
            } else {
                line = new double[]{
                    b(ellipsoid), an(ellipsoid, 2), an(ellipsoid, 4), an(ellipsoid, 6),
                    bn(ellipsoid, 2), anm(ellipsoid, 2, 2), anm(ellipsoid, 2, 4), anm(ellipsoid, 2, 6),
                    bn(ellipsoid, 4), anm(ellipsoid, 4, 2), anm(ellipsoid, 4, 4), anm(ellipsoid, 4, 6)
                };
            }

            final double[] result = new double[3];

            for (int i = 0; i < result.length; i++) {
                final int offset = i * 4;
                result[i] = line[0 + offset] + line[1 + offset] * Math.cos(2. * phip)
                        + line[2 + offset] * Math.cos(4. * phip) + line[3 + offset] * Math.cos(6. * phip);
            }
            return result;
        }

        private class LpTrials extends AbstractInitTrials implements AbsoluteDifferenceDoubleTrials {

            private final double x;

            LpTrials(final double x) {
                super(x / (a() * h0() * coefB));
                this.x = x;
            }

            @Override
            public double trial(final double l) {
                return (x / (a() * h0()) - coefA2 * Math.sin(2. * l) - coefA4 * Math.sin(4. * l)) / coefB;
            }

            @Override
            public double precision() {
                return 1e-10;
            }
        }
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    @Reference(Cosmoloj.MAP_PROJECTIONS)
    @SectionReference(type = SectionReferenceType.TABLE, number = 13)
    @Page(83)
    static final double[][] GENERAL = new double[][]{
        // Clarke 1866,
        // b, a2, a4, a6, b2, a22, a24, a26, b4, a42, a44, a46
        {0.9991507126, -0.0008471537, 0.0000021283, -0.0000000054, -0.0001412090, -0.0001411258, 0.0000000839,
            0.0000000006, -0.0000000435, -0.0000000579, -0.0000000144, 0.0},
        // International
        {0.9991565046, -0.0008413907, 0.0000020994, -0.0000000053, -0.0001402483, -0.0001401661, 0.0000000827,
            0.0000000006, -0.0000000429, -0.0000000571, -0.0000000142, 0.0}
    };
}
