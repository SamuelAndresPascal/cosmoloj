package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.MAP_PROJECTIONS)
@SectionReference(type = SectionReferenceType.SECTION, number = 10)
@Page(77)
public abstract class CylindricalEqualAreaSpheroid implements InversibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    public static Map<String, String> codes() {
        return Map.of("SNYDER", "10");
    }

    private final Spheroid spheroid;
    private final double lambda0;

    private final double r;

    protected CylindricalEqualAreaSpheroid(final Spheroid spheroid, final double lambda0) {
        this.spheroid = spheroid;
        this.lambda0 = lambda0;

        this.r = spheroid.r();
    }

    protected final double lambda0() {
        return lambda0;
    }

    protected final double r() {
        return r;
    }

    public static class Normal extends CylindricalEqualAreaSpheroid {

        private final double phis;
        private final double cosphis;

        public Normal(final Spheroid spheroid, final double phis, final double lambda0) {
            super(spheroid, lambda0);
            this.phis = phis;
            this.cosphis = Math.cos(phis);
        }

        @Override
        public double[] compute(final double[] input) {

            return new double[]{r() * cosphis * (input[LAMBDA] - lambda0()), r() / cosphis * Math.sin(input[PHI])};
        }

        @Override
        public double[] inverse(final double[] input) {
            return new double[]{
                Math.asin(input[NORTHING] / r() * cosphis),
                input[EASTING] / (r() * cosphis) + lambda0()};
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
    }

    private abstract static class H0 extends CylindricalEqualAreaSpheroid {

        private final double h0;

        protected H0(final Spheroid spheroid, final double h0, final double lambda0) {
            super(spheroid, lambda0);
            this.h0 = h0;
        }

        protected final double h0() {
            return h0;
        }
    }

    public static class Transverse extends H0 {

        private final double phi0;

        public Transverse(final Spheroid spheroid, final double h0, final double phi0, final double lambda0) {
            super(spheroid, h0, lambda0);
            this.phi0 = phi0;
        }

        @Override
        public double[] compute(final double[] input) {
            final double phi = input[PHI];
            final double l = input[LAMBDA] - lambda0();

            return new double[]{
                        r() / h0() * Math.cos(phi) * Math.sin(l),
                        r() * h0() * (Math.atan2(Math.tan(phi), Math.cos(l)) - phi0)
                    };
        }

        @Override
        public double[] inverse(final double[] input) {

            final double h = h0() / r() * input[EASTING];
            final double sqrth = Math.sqrt(1. - h * h);
            final double d = input[NORTHING] / (r() * h0()) + phi0;
            return new double[]{
                Math.asin(sqrth * Math.sin(d)),
                lambda0() + Math.atan2(h, sqrth * Math.cos(d))
            };
        }

        @Override
        public Object getParameter(final MethodParameter parameter) {
            return switch (parameter) {
                    // case LATITUDE_OF_1ST_STANDARD_PARALLEL -> phi0;
                    // case  -> h0;
                    case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0();
                    default -> throw new IllegalArgumentException();
                };
        }

        @Override
        public List<MethodParameter> getParameters() {
            return List.of(// Parameter.LATITUDE_OF_STANDARD_PARALLEL,
                        // h0
                        MethodParameter.LONGITUDE_OF_ORIGIN);
        }
    }

    public static class Oblique extends H0 {

        private final double phip;
        private final double sinphip;
        private final double cosphip;

        public Oblique(final Spheroid spheroid, final double h0, final double phip, final double lambdap) {
            super(spheroid, h0, lambdap + Math.PI / 2.);
            this.phip = phip;
            this.sinphip = Math.sin(phip);
            this.cosphip = Math.cos(phip);
        }

        @Override
        public double[] compute(final double[] input) {
            final double phi = input[PHI];
            final double l = input[LAMBDA] - lambda0();

            final double cosl = Math.cos(l);

            final double xx;
             // do not use artan2() [not working]
            if (cosl < 0.) {
                xx = Math.atan((Math.tan(phi) * cosphip + sinphip * Math.sin(l)) / cosl) + Math.PI;
            } else {
                xx = Math.atan((Math.tan(phi) * cosphip + sinphip * Math.sin(l)) / cosl);
            }
            return new double[]{
                r() * h0() * xx,
                r() / h0() * (sinphip * Math.sin(phi) - cosphip * Math.cos(phi) * Math.sin(l))
            };
        }

        @Override
        public double[] inverse(final double[] input) {

            final double yh0r = input[NORTHING] * h0() / r();
            final double xh0r = input[EASTING] / r() / h0();
            final double sinxh0r = Math.sin(xh0r);
            final double sqrtyh0r2 = Math.sqrt(1. - yh0r * yh0r);
            return new double[]{
                Math.asin(yh0r * sinphip + sqrtyh0r2 * cosphip * sinxh0r),
                lambda0() + Math.atan2(sqrtyh0r2 * sinphip * sinxh0r - yh0r * cosphip, sqrtyh0r2 * Math.cos(xh0r))
            };
        }

        @Override
        public Object getParameter(final MethodParameter parameter) {
            return switch (parameter) {
                    // case LATITUDE_OF_1ST_STANDARD_PARALLEL -> phip;
                    // case -> h0;
                    case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0();
                    default -> throw new IllegalArgumentException();
                };
        }

        @Override
        public List<MethodParameter> getParameters() {
            return List.of(// Parameter.LATITUDE_OF_STANDARD_PARALLEL,
                        // h0
                        MethodParameter.LONGITUDE_OF_ORIGIN);
        }
    }

    @Override
    public Spheroid getSurface() {
        return spheroid;
    }
}
