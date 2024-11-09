package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9832</div>
 * <div class="en">Modified Azimuthal Equidistant</div>
 *
 * @author Samuel Andrés
 *
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9832 implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double e;
    private final double e2;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;

    private final double nu0;
    private final double cosphi0;
    private final double sinphi0;

    public Epsg9832(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;
        this.e = ellipsoid.e();
        this.e2 = ellipsoid.e2();
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;

        this.nu0 = ellipsoid.nu(phi0);
        this.cosphi0 = Math.cos(phi0);
        this.sinphi0 = Math.sin(phi0);
    }

    public static Epsg9832 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9832(ellipsoid,
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
        final double l = input[LAMBDA] - lambda0;
        final double sinl = Math.sin(l);

        final double nu = ellipsoid.nu(phi);
        final double psi = Math.atan((1. - e2) * Math.tan(phi) + e2 * nu0 * sinphi0 / (nu * Math.cos(phi)));
        final double sinpsi = Math.sin(psi);
        final double cospsi = Math.cos(psi);

        final double alpha = Math.atan2(sinl, cosphi0 * Math.tan(psi) - sinphi0 * Math.cos(l));
        final double sinalpha = Math.sin(alpha);
        final double cosalpha = Math.cos(alpha);

        final double g = e * sinphi0 / Math.sqrt(1. - e2);
        final double h = e * cosphi0 * cosalpha / Math.sqrt(1. - e2);

        final double s;
        if (Math.abs(sinalpha) < 1e-9) {
            if (cosalpha < 0) {
                s = -Math.asin(cosphi0 * sinpsi - sinphi0 * cospsi);
            } else {
                s = Math.asin(cosphi0 * sinpsi - sinphi0 * cospsi);
            }
        } else {
            s = Math.asin(sinl * cospsi / sinalpha);
        }

        final double s2 = s * s;
        final double h2 = h * h;
        final double gh = g * h;

        final double c = nu0 * s * ((1. - s2 * h2 * (1. - h) / 6.)
                + s * s2 * ((1. / 8. * gh * (1. - 2. * h2))
                + s * ((1. / 120.) * ((h2 * (4. - 7. * h2)) - 3. * g * g * (1. - 7. * h2))
                - s / 48. * gh)));

        return new double[]{
            fe + c * sinalpha,
            fn + c * cosalpha
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double easting = input[EASTING];
        final double northing = input[NORTHING];

        final double x = easting - fe;
        final double y = northing - fn;

        final double c = Math.sqrt(x * x + y * y);
        final double alpha = Math.atan(x / y);
        final double sinalpha = Math.sin(alpha);
        final double cosalpha = Math.cos(alpha);

        final double coefA = -e2 * cosphi0 * cosphi0 * cosalpha * cosalpha / (1. - e2);
        final double coefB = 3. * e2 * (1. - coefA) * sinphi0 * cosphi0 * cosalpha / (1. - e2);
        final double coefD = c / nu0;
        final double coefD2 = coefD * coefD;

        final double coefJ = coefD * (1. - coefD2 * ((coefA * (1. + coefA) / 6.)
                + (coefB * (1. + 3. * coefA) * coefD / 24.)));
        final double coefJ2 = coefJ * coefJ;

        final double coefK = 1. - coefJ2 * ((coefA / 2.) + (coefB * coefJ / 6.));
        final double psi = Math.asin(sinphi0 * Math.cos(coefJ) + cosphi0 * Math.sin(coefJ) * cosalpha);

        return new double[]{
            Math.atan((1. - e2 * coefK * sinphi0 / Math.sin(psi)) * Math.tan(psi) / (1. - e2)),
            lambda0 + Math.asin(sinalpha * Math.sin(coefJ) / Math.cos(psi))
        };
    }
}
