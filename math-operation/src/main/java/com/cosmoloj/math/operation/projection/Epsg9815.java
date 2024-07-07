package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9815</div>
 * <div class="en">Hotine Oblique Mercator - Variant B</div>
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9815 extends Epsg9812 {

    private final double uc;

    public Epsg9815(final Ellipsoid ellipsoid, final double phic, final double lambdac, final double alphac,
            final double gammac, final double kc, final double fe, final double fn) {
        super(ellipsoid, phic, lambdac, alphac, gammac, kc, fe, fn);
        this.uc = uc();
    }

    public static Epsg9815 ofParams(final Ellipsoid ellipsoid, final Map<MethodParameter, ?> params) {
        return new Epsg9815(ellipsoid,
            (double) params.get(MethodParameter.LATITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.LONGITUDE_OF_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.AZIMUTH_OF_THE_INITIAL_LINE),
            (double) params.get(MethodParameter.ANGLE_FROM_THE_RECTIFIED_GRID_TO_THE_SKEW_GRID),
            (double) params.get(MethodParameter.SCALE_FACTOR_ON_THE_INITIAL_LINE),
            (double) params.get(MethodParameter.EASTING_AT_THE_PROJECTION_CENTRE),
            (double) params.get(MethodParameter.NORTHING_AT_THE_PROJECTION_CENTRE));
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LATITUDE_OF_PROJECTION_CENTRE -> phic();
            case LONGITUDE_OF_PROJECTION_CENTRE -> lambdac();
            case AZIMUTH_OF_THE_INITIAL_LINE -> alphac();
            case ANGLE_FROM_THE_RECTIFIED_GRID_TO_THE_SKEW_GRID -> gammac();
            case SCALE_FACTOR_ON_THE_INITIAL_LINE -> kc();
            case EASTING_AT_THE_PROJECTION_CENTRE -> fe();
            case NORTHING_AT_THE_PROJECTION_CENTRE -> fn();
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
                MethodParameter.EASTING_AT_THE_PROJECTION_CENTRE,
                MethodParameter.NORTHING_AT_THE_PROJECTION_CENTRE);
    }

    final double uc() {
        if (Math.abs(alphac() - Math.PI / 2.) < 1e-12) {
            return coefA() * (lambdac() - lambda0());
        } else if (phic() < 0) {
            return -coefA() / coefB() * Math.atan2(Math.sqrt(coefD() * coefD() - 1.), Math.cos(alphac()));
        } else {
            return coefA() / coefB() * Math.atan2(Math.sqrt(coefD() * coefD() - 1.), Math.cos(alphac()));
        }
    }

    @Override
    double u(final double phi, final double lambda) {
        if (Math.abs(alphac() - Math.PI / 2.) < 1e-12) {
            if (Math.abs(lambda - lambdac()) < 1e-12) {
                return 0.;
            } else {
                if (phic() < 0.) {
                    if (lambdac() - lambda < 0.) {
                        return super.u(phi, lambda) - Math.abs(uc);
                    } else {
                        return super.u(phi, lambda) + Math.abs(uc);
                    }
                } else {
                    if (lambdac() - lambda < 0.) {
                        return super.u(phi, lambda) + Math.abs(uc);
                    } else {
                        return super.u(phi, lambda) - Math.abs(uc);
                    }
                }
            }
        } else {
            if (phic() < 0.) {
                return super.u(phi, lambda) + Math.abs(uc);
            } else {
                return super.u(phi, lambda) - Math.abs(uc);
            }
        }
    }

    @Override
    final double invU(final double easting, final double northing) {
        if (phic() > 0.) {
            return super.invU(easting, northing) + Math.abs(uc);
        } else {
            return super.invU(easting, northing) - Math.abs(uc);
        }
    }
}
