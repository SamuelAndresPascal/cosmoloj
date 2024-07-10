package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.util.integral.Integral;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1028</div>
 * <div class="en">Equidistant Cylindrical</div>
 * <div class="en">See method code 1029 for spherical development. See also Pseudo Plate Carree, method code 9825.</div>
 *
 * @see Epsg1029
 * @see Epsg9825
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public abstract class Epsg1028 implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phi1;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private final double a;
    private final double e2;

    private final double nu1;
    private final double mud;
    private final double n;

    private final double f1;
    private final double f2;
    private final double f3;
    private final double f4;
    private final double f5;
    private final double f6;
    private final double f7;

    private Epsg1028(final Ellipsoid ellipsoid, final double phi1, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;
        this.phi1 = phi1;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;

        this.a = ellipsoid.a();
        this.e2 = ellipsoid.e2();
        this.nu1 = ellipsoid.nu(phi1);
        this.mud = a * (1. - e2 * (1. / 4. + e2 * (3. / 64. + e2 * (5. / 256. + e2 * (175. / 16384.
                + e2 * (441. / 65536. + e2 * (4851. / 1048576. + e2 * 14157. / 4194304.)))))));
        this.n = (1. - Math.sqrt(1. - e2)) / (1. + Math.sqrt(1. - e2));

        final double n2 = n * n;
        this.f1 = (3. / 2. + n2 * (-27. / 32. + n2 * (269. / 512. - n2 * 6607 / 24576)));
        this.f2 = (21. / 16. + n2 * (-55. / 32. + n2 * 6759. / 4096.));
        this.f3 = (151. / 96. + n2 * (-417. / 128 + n2 * 87963. / 20480.));
        this.f4 = (1097. / 512. - n2 * 15543. / 2560.);
        this.f5 = (8011. / 2560. - n2 * 69119. / 6144.);
        this.f6 = 293393. / 61440.;
        this.f7 = 6845701. / 860160.;
    }

    protected final double a() {
        return a;
    }

    protected final double e2() {
        return e2;
    }

    @Override
    public double[] compute(final double[] input) {

        return new double[]{
            fe + nu1 * Math.cos(phi1) * (input[LAMBDA] - lambda0),
            fn + m(input[PHI])
        };
    }

    abstract double m(double phi);

    public static class Series extends Epsg1028 {

        private final double m1;
        private final double m2;
        private final double m3;
        private final double m4;
        private final double m5;
        private final double m6;
        private final double m7;
        private final double m8;

        public Series(final Ellipsoid ellipsoid, final double phi1, final double lambda0, final double fe,
                final double fn) {
            super(ellipsoid, phi1, lambda0, fe, fn);

            final double e2 = e2();
            this.m1 = 1. - e2 * (1. / 4. + e2 * (3. / 64. + e2 * (5. / 256. + e2 * (175. / 16384. + e2 * (441. / 65536.
                    + e2 * (4851. / 1048576. + e2 * 14157. / 4194304.))))));
            this.m2 = -(3. / 8. + e2 * (3. / 32. + e2 * 45. / 1024. + e2 * 105. / 4096. + e2 * (2205. / 131072.
                    + e2 * (6237. / 524288. + e2 * 297297. / 33554432.))));
            this.m3 = 15. / 256. + e2 * (45. / 1024. + e2 * (525. / 16384. + e2 * (1575. / 65536.
                    + e2 * (155925. / 8388608. + e2 * 495495. / 33554432.))));
            this.m4 = -(35. / 3072 + e2 * (175. / 12288. + e2 * (3675. / 262144. + e2 * (13475. / 1048576.
                    + e2 * 385385. / 33554432.))));
            this.m5 = 315. / 131072. + e2 * (2205. / 524288. + e2 * (43659. / 8388608. + e2 * 189189. / 33554432.));
            this.m6 = -(693. / 1310720. + e2 * (6237. / 5242880. + e2 * 297297. / 167772160.));
            this.m7 = 1001. / 8388608. + e2 * 11011. / 33554432.;
            this.m8 = -6435. / 234881024;
        }

        public static Series ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
            return new Series(ellipsoid,
                (double) params.get(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL),
                (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
                (double) params.get(MethodParameter.FALSE_EASTING),
                (double) params.get(MethodParameter.FALSE_NORTHING));
        }

        @Override
        double m(final double f) {
            final double e2 = e2();
            return a() * (m1 * f
                    + e2 * (m2 * Math.sin(2. * f)
                    + e2 * (m3 * Math.sin(4. * f)
                    + e2 * (m4 * Math.sin(6. * f)
                    + e2 * (m5 * Math.sin(8. * f)
                    + e2 * (m6 * Math.sin(10. * f)
                    + e2 * (m7 * Math.sin(12. * f)
                    + e2 * m8 * Math.sin(14. * f))))))));
        }
    }

    public static class Integration3rdKind extends Epsg1028 {

        public Integration3rdKind(final Ellipsoid ellipsoid, final double phi1, final double lambda0, final double fe,
                final double fn) {
            super(ellipsoid, phi1, lambda0, fe, fn);
        }

        public static Integration3rdKind ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
            return new Integration3rdKind(ellipsoid,
                (double) params.get(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL),
                (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
                (double) params.get(MethodParameter.FALSE_EASTING),
                (double) params.get(MethodParameter.FALSE_NORTHING));
        }

        @Override
        double m(final double f) {
            return a() * (1. - e2())
                    * Integral.sum(phi -> Math.pow(1. - e2() * Math.sin(phi) * Math.sin(phi), -3. / 2.),
                            0.,
                            f,
                            (int) (50. * Math.toDegrees(f)) + 1);
        }
    }

    public static class Integration2dKind extends Epsg1028 {

        public Integration2dKind(final Ellipsoid ellipsoid, final double phi1, final double lambda0, final double fe,
                final double fn) {
            super(ellipsoid, phi1, lambda0, fe, fn);
        }

        public static Integration2dKind ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
            return new Integration2dKind(ellipsoid,
                (double) params.get(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL),
                (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
                (double) params.get(MethodParameter.FALSE_EASTING),
                (double) params.get(MethodParameter.FALSE_NORTHING));
        }

        @Override
        double m(final double f) {
            return a() * (Integral.sum(phi -> Math.sqrt(1. - e2() * Math.sin(phi) * Math.sin(phi)), 0., f,
                    (int) (4. * Math.toDegrees(f)) + 1) - e2() * Math.sin(f) * Math.cos(f) / getSurface().eSinSqrt(f));
        }
    }

    @Override
    public double[] inverse(final double[] input) {
        final double easting = input[EASTING];
        final double northing = input[NORTHING];

        final double x = easting - fe;
        final double y = northing - fn;

        final double mu = y / mud;

        return new double[]{
            f(mu),
            lambda0 + x / (nu1 * Math.cos(phi1))
        };
    }

    double f(final double m) {
        return m + n * (f1 * Math.sin(2. * m)
                + n * (f2 * Math.sin(4. * m)
                + n * (f3 * Math.sin(6. * m)
                + n * (f4 * Math.sin(8. * m)
                + n * (f5 * Math.sin(10. * m)
                + n * (f6 * Math.sin(12. * m)
                + n * f7 * Math.sin(14. * m)))))));
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_1ST_STANDARD_PARALLEL -> phi1;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_1ST_STANDARD_PARALLEL,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public final Ellipsoid getSurface() {
        return ellipsoid;
    }
}
