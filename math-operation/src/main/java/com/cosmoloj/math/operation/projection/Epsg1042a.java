package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Cite;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::1042</div>
 * <div class="en">Krovak Modified</div>
 *
 * @author Samuel Andrés
 */
@Cite(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg1042a extends Epsg9819a {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    protected static final int SOUTHING = 0;
    protected static final int WESTING = 1;

    private final double c1;
    private final double c2;
    private final double c3;
    private final double c4;
    private final double c5;
    private final double c6;
    private final double c7;
    private final double c8;
    private final double c9;
    private final double c10;
    private final double x0;
    private final double y0;

    public Epsg1042a(final Ellipsoid ellipsoid, final double phic, final double lambda0, final double alphac,
            final double phip, final double kp, final double ef, final double nf, final double x0, final double y0,
            final double c1, final double c2, final double c3, final double c4, final double c5, final double c6,
            final double c7, final double c8, final double c9, final double c10) {
        super(ellipsoid, phic, lambda0, alphac, phip, kp, ef, nf);
        this.x0 = x0;
        this.y0 = y0;
        this.c1 = c1;
        this.c2 = c2;
        this.c3 = c3;
        this.c4 = c4;
        this.c5 = c5;
        this.c6 = c6;
        this.c7 = c7;
        this.c8 = c8;
        this.c9 = c9;
        this.c10 = c10;
    }

    public static Epsg1042a ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg1042a(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.LONGITUDE_OF_ORIGIN),
            (double) params.get(MethodParameter.COLATITUDE_OF_THE_CONE_AXIS),
            (double) params.get(MethodParameter.LATITUDE_OF_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL),
            (double) params.get(MethodParameter.FALSE_EASTING),
            (double) params.get(MethodParameter.FALSE_NORTHING),
            (double) params.get(MethodParameter.ORDINATE_1_OF_EVALUATION_POINT),
            (double) params.get(MethodParameter.ORDINATE_2_OF_EVALUATION_POINT),
            (double) params.get(MethodParameter.C1),
            (double) params.get(MethodParameter.C2),
            (double) params.get(MethodParameter.C3),
            (double) params.get(MethodParameter.C4),
            (double) params.get(MethodParameter.C5),
            (double) params.get(MethodParameter.C6),
            (double) params.get(MethodParameter.C7),
            (double) params.get(MethodParameter.C8),
            (double) params.get(MethodParameter.C9),
            (double) params.get(MethodParameter.C10));
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case ORDINATE_1_OF_EVALUATION_POINT -> x0;
            case ORDINATE_2_OF_EVALUATION_POINT -> y0;
            case C1 -> c1;
            case C2 -> c2;
            case C3 -> c3;
            case C4 -> c4;
            case C5 -> c5;
            case C6 -> c6;
            case C7 -> c7;
            case C8 -> c8;
            case C9 -> c9;
            case C10 -> c10;
            default -> super.getParameter(parameter);
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE,
                MethodParameter.LONGITUDE_OF_ORIGIN,
                MethodParameter.COLATITUDE_OF_THE_CONE_AXIS,
                MethodParameter.LATITUDE_OF_PSEUDO_STANDARD_PARALLEL,
                MethodParameter.SCALE_FACTOR_ON_PSEUDO_STANDARD_PARALLEL,
                MethodParameter.FALSE_EASTING,
                MethodParameter.FALSE_NORTHING,
                MethodParameter.ORDINATE_1_OF_EVALUATION_POINT,
                MethodParameter.ORDINATE_2_OF_EVALUATION_POINT,
                MethodParameter.C1,
                MethodParameter.C2,
                MethodParameter.C3,
                MethodParameter.C4,
                MethodParameter.C5,
                MethodParameter.C6,
                MethodParameter.C7,
                MethodParameter.C8,
                MethodParameter.C9,
                MethodParameter.C10);
    }

    @Override
    public double[] compute(final double[] input) {
        final double coefU = coefU(input[PHI]);
        final double coefV = coefV(input[LAMBDA]);
        final double coefT = coefT(coefU, coefV);
        final double r = r(coefU, coefV, coefT);
        final double theta = theta(coefU, coefV, coefT);
        final double xp = r * Math.cos(theta);
        final double yp = r * Math.sin(theta);
        final double xr = xp - x0;
        final double yr = yp - y0;
        return new double[]{xp - dx(xr, yr) + fn(), yp - dy(xr, yr) + fe()};
    }

    @Override
    public double[] inverse(final double[] input) {
        final double invXp = invXp(input);
        final double invYp = invYp(input);
        final double invT = invT(invXp, invYp);
        final double invD = invD(invXp, invYp);
        final double invU = invU(invT, invD);
        return new double[]{phi(invU), lambda(invT, invD, invU)};
    }

    double dx(final double x, final double y) {
        final double x2 = x * x;
        final double y2 = y * y;

        return c1 + c3 * x - c4 * y - 2. * c6 * x * y + c5 * (x2 - y2) + c7 * x * (x2 - 3. * y2)
                - c8 * y * (3. * x2 - y2) + 4. * c9 * x * y * (x2 - y2)
                + c10 * (x2 * x2 + y2 * y2 - 6. * x2 * y2);
    }

    double dy(final double x, final double y) {
        final double x2 = x * x;
        final double y2 = y * y;
        return c2 + c3 * y + c4 * x + 2. * c5 * x * y + c6 * (x2 - y2) + c8 * x * (x2 - 3. * y2)
                + c7 * y * (3. * x2 - y2) - 4. * c10 * x * y * (x2 - y2)
                + c9 * (x2 * x2 + y2 * y2 - 6. * x2 * y2);
    }

    double invXp(final double[] input) {
        final double xrp = input[SOUTHING] - fn() - x0;
        final double yrp = input[WESTING] - fe() - y0;
        return input[SOUTHING] - fn() + dx(xrp, yrp);
    }

    double invYp(final double[] input) {
        final double xrp = input[SOUTHING] - fn() - x0;
        final double yrp = input[WESTING] - fe() - y0;
        return input[WESTING] - fe() + dy(xrp, yrp);
    }
}
