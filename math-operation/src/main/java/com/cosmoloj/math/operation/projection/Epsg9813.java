package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.util.complex.Complex;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9813</div>
 * <div class="en">Laborde Oblique Mercator</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9813 implements InvertibleProjection {

    protected static final int PHI = 0;
    protected static final int LAMBDA = 1;
    protected static final int EASTING = 0;
    protected static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;
    private final double phic;
    private final double lambdac;
    private final double alphac;
    private final double kc;
    private final double fe;
    private final double fn;

    private final double a;
    private final double e;
    private final double e2;
    private final double coefB;
    private final double phis;
    private final double coefR;
    private final double coefC;
    private final Complex coefG;

    public Epsg9813(final Ellipsoid ellipsoid, final double phic, final double lambdac, final double alphac,
            final double kc, final double fe, final double fn) {
        this.ellipsoid = ellipsoid;
        this.phic = phic;
        this.lambdac = lambdac;
        this.alphac = alphac;
        this.kc = kc;
        this.fe = fe;
        this.fn = fn;

        final double cosphic = Math.cos(phic);
        final double sinphic = Math.sin(phic);
        this.e = ellipsoid.e();
        this.e2 = e * e;
        this.a = ellipsoid.a();
        this.coefB = Math.sqrt(1. + (e2 * cosphic * cosphic * cosphic * cosphic) / (1. - e2));
        this.phis = Math.asin(sinphic / coefB);
        this.coefR = a * kc * (Math.sqrt(1. - e2) / (1. - e2 * sinphic * sinphic));
        this.coefC = Math.log(Math.tan(Math.PI / 4. + phis / 2.))
                - coefB * Math.log(Math.tan(Math.PI / 4. + phic / 2.)
                * Math.pow((1. - e * sinphic) / (1. + e * sinphic), e / 2.));
        this.coefG = Complex.of(1. - Math.cos(2. * alphac), Math.sin(2. * alphac)).div(12.);
    }

    public static Epsg9813 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9813(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.LONGITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.AZIMUTH_OF_THE_INITIAL_LINE),
            (double) params.get(MethodParameter.SCALE_FACTOR_ON_THE_INITIAL_LINE),
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
            case LATITUDE_OF_PROJECTION_CENTRE -> phic;
            case LONGITUDE_OF_PROJECTION_CENTRE -> lambdac;
            case AZIMUTH_OF_THE_INITIAL_LINE -> alphac;
            case SCALE_FACTOR_ON_THE_INITIAL_LINE -> kc;
            case FALSE_EASTING -> fe;
            case FALSE_NORTHING -> fn;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN,
                MethodParameter.AZIMUTH_OF_THE_INITIAL_LINE,
                MethodParameter.ANGLE_FROM_THE_RECTIFIED_GRID_TO_THE_SKEW_GRID,
                MethodParameter.SCALE_FACTOR_ON_THE_INITIAL_LINE,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING);
    }

    @Override
    public double[] compute(final double[] input) {
        final double phi = input[PHI];
        final double lambda = input[LAMBDA];

        final double l = coefL(lambda);
        final double p = coefP(phi);

        final double u = coefU(p, l);
        final double v = coefV(p, l);
        final double w = coefW(p, l);

        final double d = d(u, v);

        final double lp = coefLp(u, v);
        final double pp = coefPp(w, d);

        final Complex h = coefH(lp, pp);

        final Complex hPlusGH3 = h.add(this.coefG.mult(h).mult(h).mult(h));

        return new double[]{fe + coefR * hPlusGH3.getImaginary(), fn + coefR * hPlusGH3.getReal()};
    }

    @Override
    public double[] inverse(final double[] input) {
        final double easting = input[EASTING];
        final double northing = input[NORTHING];

        final Complex hk = coefHk(easting, northing);

        final double lp = coefLp(hk);
        final double pp = coefPp(hk);

        final double up = coefUp(pp, lp);
        final double vp = coefVp(pp);
        final double wp = coefWp(pp, lp);

        final double d = d(up, vp);

        final double l;
        final double p;

        if (Math.abs(d) < 1e-12) {
            l = 0.;
            p = wp < 0. ? -Math.PI / 2. : Math.PI / 2.;
        } else {
            l = 2. * Math.atan2(vp, up + d);
            p = Math.atan2(wp, d);
        }

        final double qp = (Math.log(Math.tan(Math.PI / 4. + p / 2.)) - coefC) / coefB;

        return new double[]{phik(qp), lambdac + l / coefB};
    }

    double phik(final double qp) {
        final double phi0 = 2. * Math.atan(Math.exp(qp)) - Math.PI / 2.;
        double phik = phik(phi0, qp);

        while (true) {
            final double tmp = phik(phik, qp);
            if (Math.abs(tmp - phik) < 1e-11) {
                return tmp;
            } else {
                phik = tmp;
            }
        }

    }

    double phik(final double phik, final double qp) {
        final double esinphik = e * Math.sin(phik);
        return 2. * Math.atan(Math.pow((1. + esinphik) / (1. - esinphik), e / 2.) * Math.exp(qp)) - Math.PI / 2.;
    }

    Complex coefH0(final double easting, final double northing) {
        return Complex.of((northing - fn) / coefR, (easting - fe) / coefR);
    }

    Complex coefH1(final Complex h0, final Complex g) {
        return h0.div(h0.add(g.mult(h0.mult(h0.mult(h0)))));
    }

    Complex coefHk(final Complex h0, final Complex g, final Complex hk) {
        return h0.add(g.mult(2.).mult(hk.mult(hk.mult(hk))))
                .div(g.mult(hk.mult(hk)).mult(3.).add(Complex.UNIT_REAL));
    }

    Complex coefHk(final double easting, final double northing) {
        final Complex h0 = coefH0(easting, northing);
        final Complex h1 = coefH1(h0, this.coefG);
        Complex hk = coefHk(h0, this.coefG, h1);
        while (true) {
            if (Math.abs(h0.add(hk.opposite()).add(this.coefG.mult(hk.mult(hk.mult(hk))).opposite()).getReal())
                    < 1e-11) {
                return hk;
            } else {
                hk = coefHk(h0, this.coefG, hk);
            }
        }
    }

    double coefLp(final Complex hk) {
        return -hk.getReal();
    }

    double coefPp(final Complex hk) {
        return 2. * Math.atan(Math.exp(hk.getImaginary())) - Math.PI / 2.;
    }

    double coefUp(final double p, final double l) {
        return Math.cos(p) * Math.cos(l) * Math.cos(phis) + Math.cos(p) * Math.sin(l) * Math.sin(phis);
    }

    double coefVp(final double p) {
        return Math.sin(p);
    }

    double coefWp(final double p, final double l) {
        return Math.cos(p) * Math.cos(l) * Math.sin(phis) - Math.cos(p) * Math.sin(l) * Math.cos(phis);
    }

    double coefL(final double lambda) {
        return coefB * (lambda - lambdac);
    }

    double q(final double phi) {
        final double sinphi = Math.sin(phi);
        return coefC + coefB * Math.log(Math.tan(Math.PI / 4. + phi / 2.)
                * Math.pow((1. - e * sinphi) / (1. + e * sinphi), e / 2.));
    }

    double coefP(final double phi) {
        return 2. * Math.atan(Math.exp(q(phi))) - Math.PI / 2.;
    }

    double coefU(final double p, final double l) {
        return Math.cos(p) * Math.cos(l) * Math.cos(phis) + Math.sin(p) * Math.sin(phis);
    }

    double coefV(final double p, final double l) {
        return Math.cos(p) * Math.cos(l) * Math.sin(phis) - Math.sin(p) * Math.cos(phis);
    }

    double coefW(final double p, final double l) {
        return Math.cos(p) * Math.sin(l);
    }

    double d(final double u, final double v) {
        return Math.sqrt(u * u + v * v);
    }

    double coefLp(final double u, final double v) {
        final double d = d(u, v);
        if (Math.abs(d) < 1e-12) {
            return 0.;
        } else {
            return 2. * Math.atan2(v, u + d);
        }
    }

    double coefPp(final double w, final double d) {
        if (Math.abs(d) < 1e-12) {
            return (w > 0. ? Math.PI : -Math.PI) / 2.;
        } else {
            return Math.atan(w / d);
        }
    }

    Complex coefH(final double lp, final double pp) {
        return Complex.of(-lp, Math.log(Math.tan(Math.PI / 4. + pp / 2.)));
    }
}
