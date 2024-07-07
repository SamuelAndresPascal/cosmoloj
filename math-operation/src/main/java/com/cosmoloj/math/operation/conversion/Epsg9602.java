package com.cosmoloj.math.operation.conversion;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.trials.AbstractInitTrials;
import java.util.List;
import com.cosmoloj.math.operation.trials.AbsoluteDifferenceDoubleTrials;
import com.cosmoloj.util.bib.Reference;

/**
 * <div>EPSG::9602</div>
 * <div class="en">Geographic/Geocentric conversions</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9602 implements CoordinateConversion, AutoInverse<double[]> {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int H = 2;
    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double b;
    private final double e2;
    private final double epsilon;

    public Epsg9602(final Ellipsoid ellipsoid) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.b = ellipsoid.b();
        this.e2 = ellipsoid.e2();
        this.epsilon = ellipsoid.secondE() * ellipsoid.secondE();
    }

    public static Epsg9602 ofParams(final Ellipsoid ellipsoid) {
        return new Epsg9602(ellipsoid);
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

        final double phi = input[PHI];

        final double nu = ellipsoid.nu(phi);
        final double h = input[H];
        final double nuh = nu + h;
        final double cosphi = Math.cos(phi);
        final double lambda = input[LAMBDA];
        return new double[]{
            nuh * cosphi * Math.cos(lambda),
            nuh * cosphi * Math.sin(lambda),
            ((1. - e2) * nu + h) * Math.sin(phi)
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double x = input[X];
        final double y = input[Y];
        final double z = input[Z];

        final double p = Math.sqrt(x * x + y * y);
        final double q = Math.atan2(z * a, p * b);
        final double sinq = Math.sin(q);
        final double cosq = Math.cos(q);
        final double phi = this.new Phi(
                Math.atan2(z + epsilon * b * sinq * sinq * sinq, p - e2 * a * cosq * cosq * cosq), p, z).loop();
        final double lambda = Math.atan2(y, x);
        return new double[]{
            phi,
            lambda,
            p / Math.cos(phi) - ellipsoid.nu(phi)
        };
    }

    private class Phi extends AbstractInitTrials implements AbsoluteDifferenceDoubleTrials {

        private final double p;
        private final double z;

        Phi(final double init, final double p, final double z) {
            super(init);
            this.p = p;
            this.z = z;
        }

        @Override
        public double trial(final double phi) {
            return Math.atan2((z + e2 * ellipsoid.nu(phi) * Math.sin(phi)), p);
        }

        @Override
        public double precision() {
            return 1e-9;
        }
    }
}
