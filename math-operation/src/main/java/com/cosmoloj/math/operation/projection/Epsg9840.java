package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.conversion.Epsg9837;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.trials.AbsoluteDifferenceDoubleVectorTrials;
import com.cosmoloj.util.bib.Reference;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9840</div>
 * <div class="en">Orthographic</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg9837
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9840 implements InversibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;
    private static final int U = 0;
    private static final int V = 1;

    private final Ellipsoid ellipsoid;
    private final Epsg9837 geotopo;
    private final double e2;

    private final double phi0;
    private final double sinphi0;
    private final double cosphi0;
    private final double lambda0;
    private final double fe;
    private final double fn;
    private final double[] init;
    private final double nu0;

    public Epsg9840(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;
        this.e2 = ellipsoid.e2();
        this.geotopo = new Epsg9837(ellipsoid, phi0, lambda0, 0.); // h0 = 0. is an arbitrary value
        this.fe = fe;
        this.fn = fn;
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.init = new double[]{phi0, lambda0};
        this.sinphi0 = Math.sin(phi0);
        this.cosphi0 = Math.cos(phi0);
        this.nu0 = ellipsoid.nu(phi0);
    }

    public static Epsg9840 ofParams(final Ellipsoid spheroid, final Map<MethodParameter, ?> params) {
        return new Epsg9840(spheroid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    @Override
    public double[] compute(final double[] input) {
        final double[] topo = geotopo.compute(Arrays.copyOf(input, 3));
        return new double[]{fe + topo[U], fn + topo[V]};
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
    public double[] inverse(final double[] input) {

        return this.new InverseTrials(input).loop();
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }

    private class InverseTrials implements AbsoluteDifferenceDoubleVectorTrials {

        private final double easting;
        private final double northing;

        InverseTrials(final double[] input) {
            this.easting = input[EASTING];
            this.northing = input[NORTHING];
        }

        @Override
        public double[] trial(final double[] input) {
            final double phi = input[PHI];
            final double l = input[LAMBDA] - lambda0;
            final double sinl = Math.sin(l);
            final double cosl = Math.cos(l);
            final double nu = ellipsoid.nu(phi);
            final double rho = ellipsoid.rho(phi);
            final double cosphi = Math.cos(phi);
            final double sinphi = Math.sin(phi);

            final double e = fe + nu * cosphi * sinl;
            final double n = fn + nu * (sinphi * cosphi0 - cosphi * sinphi0 * cosl)
                    + e2 * (nu0 * sinphi0 - nu * sinphi) * cosphi0;

            final double j11 = -rho * sinphi * sinl;
            final double j12 = nu * cosphi * cosl;
            final double j21 = rho * (cosphi * cosphi0 + sinphi * sinphi0 * cosl);
            final double j22 = nu * sinphi0 * cosphi * sinl;

            final double d = j11 * j22 - j12 * j21;

            final double de = easting - e;
            final double dn = northing - n;

            return new double[]{
                phi + (j22 * de - j12 * dn) / d,
                input[LAMBDA] + (j11 * dn - j21 * de) / d
            };
        }

        @Override
        public double[] init() {
            return init;
        }

        @Override
        public double precision() {
            return 1e-9;
        }
    }
}
