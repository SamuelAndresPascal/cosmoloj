package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9809</div>
 * <div class="en">Oblique and Equatorial Stereographic</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9809 implements InvertibleProjection {

    protected static final int PHI = 0;
    protected static final int LAMBDA = 1;
    protected static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phi0;
    private final double lambda0;
    private final double fe;
    private final double fn;
    private final double k0;

    private final double e;
    private final double e2;
    private final double r;
    private final double n;
    private final double c;
    private final double khi0;
    private final double lambda0UC;

    public Epsg9809(final Ellipsoid ellipsoid, final double phi0, final double lambda0, final double k0,
            final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.phi0 = phi0;
        this.lambda0 = lambda0;
        this.fe = fe;
        this.fn = fn;
        this.k0 = k0;

        this.e = ellipsoid.e();
        this.e2 = e * e;
        this.r = Math.sqrt(ellipsoid.rho(phi0) * ellipsoid.nu(phi0));
        final double cosphi0 = Math.cos(phi0);
        this.n = Math.sqrt(1. + (e2  * cosphi0  * cosphi0  * cosphi0  * cosphi0 / (1. - e2)));
        final double sinphi0 = Math.sin(phi0);
        final double s1 = (1. + sinphi0) / (1. - sinphi0);
        final double s2 = (1. - ellipsoid.e() * sinphi0) / (1. + ellipsoid.e() * sinphi0);
        final double w1 = Math.pow(s1 * Math.pow(s2, ellipsoid.e()), this.n);
        final double sinkhi0 = (w1 - 1.) / (w1 + 1.);
        this.c = (n + sinphi0) * (1. - sinkhi0) / ((n - sinphi0) * (1. + sinkhi0));
        final double w2 = c * w1;
        this.khi0 = Math.asin((w2 - 1.) / (w2 + 1.));
        this.lambda0UC = lambda0;
    }

    public static Epsg9809 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9809(ellipsoid,
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
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];

        final double lambdaUC = n * (lambda - lambda0UC) + lambda0UC;
        final double sinphi = Math.sin(phi);
        final double esinphi = e * sinphi;
        final double sa = (1. + sinphi) / (1. - sinphi);
        final double sb = (1. - esinphi) / (1. + esinphi);
        final double w = c * Math.pow(sa * Math.pow(sb, e), n);

        final double khi = Math.asin((w - 1.) / (w + 1.));

        final double coefB = 1. + Math.sin(khi) * Math.sin(khi0)
                + Math.cos(khi) * Math.cos(khi0) * Math.cos(lambdaUC - lambda0UC);

        return new double[]{
            fe + 2. * r * k0 * Math.cos(khi) * Math.sin(lambdaUC - lambda0UC) / coefB,
            fn + 2. * r * k0 * (Math.sin(khi) * Math.cos(khi0)
                - Math.cos(khi) * Math.sin(khi0) * Math.cos(lambdaUC - lambda0UC)) / coefB
        };
    }

    @Override
    public double[] inverse(final double[] input) {
        final double northing = input[NORTHING];
        final double easting = input[EASTING];
        final double g = 2. * r * k0 * Math.tan(Math.PI / 4. - khi0 / 2.);
        final double h = 4. * r * k0 * Math.tan(khi0) + g;
        final double i = Math.atan2(easting - fe, h + (northing - fn));
        final double j = Math.atan2(easting - fe, g - (northing - fn)) - i;
        final double khi = khi0 + 2 * Math.atan(((northing - fn) - (easting - fe) * Math.tan(j / 2.)) / (2. * r * k0));
        final double lambdaUC = j + 2 * i + lambda0UC;

        final double lambda = (lambdaUC - lambda0UC) / n + lambda0UC;

        final double psi = .5 * Math.log((1. + Math.sin(khi)) / (c * (1. - Math.sin(khi)))) / n;


        final double phi1 = 2. * Math.atan(Math.exp(psi)) - Math.PI / 2.;

        double phii = phii(phi1, psi);

        double phi;
        while (true) {
            phi = phii(phii, psi);
            if (Math.abs(phi - phii) < 1e-11) {
                break;
            } else {
                phii = phi;
            }
        }

        return new double[]{phi, lambda};
    }

    double phii(final double phii, final double psi) {
        final double esinphi = e * Math.sin(phii);
        final double psii = Math.log(Math.tan(phii / 2. + Math.PI / 4.)
                * Math.pow((1. - esinphi) / (1. + esinphi), e / 2.));
        return phii - (psii - psi) * Math.cos(phii) * (1. - esinphi * esinphi) / (1. - e2);
    }
}
