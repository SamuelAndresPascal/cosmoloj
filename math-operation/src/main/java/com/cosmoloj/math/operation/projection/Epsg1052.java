package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1052</div>
 * <div class="en">Colombia Urban</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1052 implements InversibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double e2;
    private final double coefA;
    private final double coefB;
    private final double coefC;
    private final double coefD;

    private final double phi0;
    private final double lambda0;
    private final double h0;
    private final double fe;
    private final double fn;
    private final double nu0;
    private final double rho0;

    public Epsg1052(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double h0,
            final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.e2 = ellipsoid.e2();
        this.h0 = h0;
        this.fe = fe;
        this.fn = fn;
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.nu0 = ellipsoid.nu(phi0);
        this.coefA = 1. + h0 / nu0;
        this.rho0 = ellipsoid.rho(phi0);
        this.coefB = Math.tan(phi0) / (2. * rho0 * nu0);
        this.coefC = 1. + h0 / ellipsoid.a();
        this.coefD = rho0 * (1. + h0 / (ellipsoid.a() * (1. - e2)));
    }

    public static Epsg1052 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1052(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING),
            (double) params.get(MethodParameter.PROJECTION_PLANE_ORIGIN_HEIGHT));
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double l = input[LAMBDA] - lambda0;
        final double phim = (phi0 + phi) / 2.;
        final double rhom = ellipsoid.rho(phim);
        final double nu = ellipsoid.nu(phi);
        final double coefG = 1 + h0 / rhom;
        final double cosphi = Math.cos(phi);
        return new double[]{
            fe + coefA * nu * cosphi * l,
            fn + coefG * rho0 * ((phi - phi0) + (coefB * l * l * nu * nu * cosphi * cosphi))
        };
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_NATURAL_ORIGIN -> phi0;
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            case PROJECTION_PLANE_ORIGIN_HEIGHT -> h0;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING,
                MethodParameter.PROJECTION_PLANE_ORIGIN_HEIGHT);
    }

    @Override
    public double[] inverse(final double[] input) {
        final double de = (input[EASTING] - fe) / coefC;
        final double phi = phi0 + (input[NORTHING] - fn) / coefD - coefB * de * de;
        return new double[]{
            phi,
            lambda0 + de / (ellipsoid.nu(phi) * Math.cos(phi))
        };
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }
}
