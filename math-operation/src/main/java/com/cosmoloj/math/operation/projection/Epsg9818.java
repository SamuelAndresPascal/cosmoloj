package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9818</div>
 * <div class="en">American Polyconic</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9818 implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;
    private final double m0;

    private final double e2;

    public Epsg9818(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double fe,
            final double fn) {
        this.ellipsoid = ellipsoid;
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;

        this.m0 = ellipsoid.m(phi0);
        this.e2 = ellipsoid.e() * ellipsoid.e();
    }

    public static Epsg9818 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9818(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING));
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

        if (Math.abs(phi) < 1e-9) {
            return new double[]{
                fe + ellipsoid.a() * (lambda - lambda0),
                fn - m0};
        } else {
            final double l = (lambda - lambda0) * Math.sin(phi);
            final double nucotanphi = ellipsoid.nu(phi) / Math.tan(phi);
            return new double[]{
                fe + nucotanphi * Math.sin(l),
                fn + ellipsoid.m(phi) - m0 + nucotanphi * (1. - Math.cos(l))
            };
        }
    }

    @Override
    public double[] inverse(final double[] input) {
        final double easting = input[EASTING];
        final double northing = input[NORTHING];

        final double l = (easting - fe) / ellipsoid.a();
        if (Math.abs(northing - fn) < Math.abs(m0)) {
            return new double[]{0., lambda0 + l};
        } else {
            final double coefA = (m0 + (northing - fn)) / ellipsoid.a();
            final double coefB = coefA * coefA + l * l;

            double coefC = coefC(coefA);
            double phin = phin(coefA, coefA, coefB, coefC);

            while (true) {
                coefC = coefC(phin);
                final double tmp = phin(phin, coefA, coefB, coefC);
                if (Math.abs(tmp - phin) < 1e-9) {
                    phin = tmp;
                    break;
                } else {
                    phin = tmp;
                }
            }
            return new double[]{phin,
                lambda0 + Math.asin((easting - fe) * coefC / ellipsoid.a()) / Math.sin(phin)};
        }
    }

    double coefC(final double phi) {
        return Math.tan(phi) / ellipsoid.nu(phi) * ellipsoid.a();
    }

    double phin(final double phin, final double a, final double b, final double c) {
        final double ma = ellipsoid.m(phin) / ellipsoid.a();
        final double mnp = ellipsoid.mp(phin);
        return phin - (a  * (c * ma + 1.) - ma - 1. / 2. * (ma * ma + b) * c)
                / (e2 * Math.sin(2. * phin) * (ma * ma + b - 2. * a * ma) / (4. * c)
                + (a - ma) * (c * mnp - 2. / Math.sin(2. * phin)) - mnp);
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }
}
