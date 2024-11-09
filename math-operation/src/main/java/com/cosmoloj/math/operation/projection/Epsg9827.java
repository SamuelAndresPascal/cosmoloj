package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9827</div>
 * <div class="en">Bonne</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9827 implements InvertibleProjection {

    protected static final int PHI = 0;
    protected static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double a;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private final double m0;
    private final double coefM0;

    public Epsg9827(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;
        this.a = ellipsoid.a();
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;

        this.m0 = m(phi0);
        this.coefM0 = ellipsoid.m(phi0);
    }

    public static Epsg9827 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9827(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
    }

    protected final double a() {
        return a;
    }

    protected final double phi0() {
        return phi0;
    }

    protected final double lambda0() {
        return lambda0;
    }

    protected final double fe() {
        return fe;
    }

    protected final double fn() {
        return fn;
    }


    protected final double m0() {
        return m0;
    }

    protected final double coefM0() {
        return coefM0;
    }


    @Override
    public final Ellipsoid getSurface() {
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

        final double m = m(phi);

        final double coefM = ellipsoid.m(phi);

        final double rho = a * m0 / Math.sin(phi0) + coefM0 - coefM;

        final double coefT = a * m * (lambda - lambda0) / rho;

        return new double[]{
            rho * Math.sin(coefT) + fe,
            (a * m0 / Math.sin(phi0) - rho * Math.cos(coefT)) + fn
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double easting = input[EASTING];
        final double northing = input[NORTHING];

        final double x = easting - fe;
        final double y = northing - fn;
        final double yRho = (a * m0 / Math.sin(phi0) - y);

        final double rho;

        if (phi0 < 0.) {
            rho = -Math.sqrt(x * x + yRho * yRho);
        } else {
            rho = Math.sqrt(x * x + yRho * yRho);
        }

        final double coefM = a * m0 / Math.sin(phi0) + coefM0 - rho;

        final double mu = ellipsoid.mu(coefM);

        final double phi = ellipsoid.phi1(mu);

        final double m = m(phi);

        final double preLambda;
        if (Math.abs(phi - Math.PI) < 1e-9 || Math.abs(phi + Math.PI) < 1e-9
                || Math.abs(m) < 1e-9) {
            preLambda = 0.;
        } else if (phi0 < 0.) {
            preLambda = Math.atan2(x, a * m0 / Math.sin(phi0) - y);
        } else {
            preLambda = Math.atan2(x, a * m0 / Math.sin(phi0) - y);
        }

        return new double[]{
            phi,
            lambda0 + rho * preLambda
        };
    }

    final double m(final double phi) {
        return Math.cos(phi) / ellipsoid.eSinSqrt(phi);
    }
}
