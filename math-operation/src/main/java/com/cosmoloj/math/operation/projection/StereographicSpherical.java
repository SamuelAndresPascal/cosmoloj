package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.MAP_PROJECTIONS)
@SectionReference(type = SectionReferenceType.SECTION, number = 21)
@Page(154)
public class StereographicSpherical implements InversibleProjection {

    private final Spheroid spheroid;
    private final double r; // R
    private final double standardParallel; // Phi 1
    private final double centralLongitude; // Lambda 0
    private final double centralScaleFactor; // k 0

    private final double sinPhi1;
    private final double cosPhi1;

    private final double[] project = new double[2];
    private final double[] unproject = new double[2];

    public StereographicSpherical(final Spheroid spheroid, final double standardParallel,
            final double centralLongitude, final double centralScaleFactor) {
        this.spheroid = spheroid;
        this.r = spheroid.r();
        this.standardParallel = standardParallel;

        sinPhi1 = Math.sin(standardParallel);
        cosPhi1 = Math.cos(standardParallel);

        this.centralLongitude = centralLongitude;
        this.centralScaleFactor = centralScaleFactor;
    }

    public static StereographicSpherical ofParams(final Spheroid spheroid, final Map<MethodParameter, ?> params) {
        return new StereographicSpherical(spheroid,
                (double) params.get(MethodParameter.STANDARD_PARALLEL),
                (double) params.get(MethodParameter.CENTRAL_LONGITUDE),
                (double) params.get(MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN));
    }

    @Override
    public Spheroid getSurface() {
        return spheroid;
    }

    @Override
    public double[] compute(final double[] input) {
        final double lat = input[0];
        final double lon = input[1];
        final double scaleFactor
                = MapProjections.k_21_4(cosPhi1, sinPhi1, centralLongitude, centralScaleFactor, lat, lon);
        project[0] = MapProjections.x_21_2(r, centralLongitude, lat, lon, scaleFactor);
        project[1] = MapProjections.y_21_3(r, cosPhi1, sinPhi1, centralLongitude, lat, lon, scaleFactor);
        return project;
    }

    @Override
    public double[] inverse(final double[] input) {
        final double x = input[0];
        final double y = input[1];
        final double rho = MapProjections.rho_20_18(x, y);
        final double c = MapProjections.c_21_15(r, centralScaleFactor, rho);
        final double cosC = Math.cos(c);
        final double sinC = Math.sin(c);
        unproject[0] = MapProjections.phi_20_14(cosPhi1, sinPhi1, rho, cosC, sinC, y);
        unproject[1] = MapProjections.lambda_20_15(cosPhi1, sinPhi1, centralLongitude, rho, cosC, sinC, x, y);
        return unproject;
    }

    @Override
    public boolean canProject(final double[] input) {
        return Math.abs(MapProjections.inverseShiftTransform(
                input[0], input[1], standardParallel, centralLongitude, 0.)[0]) - Math.PI / 2. < 1e-9;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case CENTRAL_LONGITUDE -> centralLongitude;
            case STANDARD_PARALLEL -> standardParallel;
            case SCALE_FACTOR_AT_NATURAL_ORIGIN -> centralScaleFactor;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.CENTRAL_LONGITUDE,
                MethodParameter.STANDARD_PARALLEL,
                MethodParameter.SCALE_FACTOR_AT_NATURAL_ORIGIN);
    }
}
