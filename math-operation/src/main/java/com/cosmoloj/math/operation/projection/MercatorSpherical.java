package com.cosmoloj.math.operation.projection;

import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Spheroid;
import com.cosmoloj.util.bib.Page;
import com.cosmoloj.util.bib.Reference;
import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.util.bib.SectionReference;
import com.cosmoloj.util.bib.SectionReferenceType;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.MAP_PROJECTIONS)
@SectionReference(type = SectionReferenceType.SECTION, number = 7)
@Page(38)
public class MercatorSpherical implements InversibleProjection {

    private final Spheroid spheroid;
    private final double r; // R
    private final double phi0;
    private final double lambda0; // Lambda 0

    private final double cosPhi1;

    private final double[] project = new double[2];
    private final double[] unproject = new double[2];

    public MercatorSpherical(final Spheroid spheroid, final double phi0, final double lambda0) {
        this.spheroid = spheroid;
        this.r = spheroid.r();
        this.phi0 = phi0;
        this.cosPhi1 = Math.cos(phi0);
        this.lambda0 = lambda0;
    }

    protected final double phi0() {
        return phi0;
    }

    protected final double lambda0() {
        return lambda0;
    }

    @Override
    public Spheroid getSurface() {
        return spheroid;
    }

    @Override
    public boolean canProject(final double[] input) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public double[] compute(final double[] input) {
        final double lat = input[0];
        final double lon = input[1];
        project[0] = MapProjections.x_7_1(r, cosPhi1, lambda0, lon);
        project[1] = MapProjections.y_7_2(r, cosPhi1, lat);
        return project;
    }

    @Override
    public double[] inverse(final double[] input) {
        final double x = input[0];
        final double y = input[1];
        unproject[0] = MapProjections.phi_7_4(r, cosPhi1, y);
        unproject[1] = MapProjections.lambda_7_5(r, cosPhi1, lambda0, x);
        return unproject;
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        return switch (parameter) {
            case LONGITUDE_OF_NATURAL_ORIGIN -> lambda0;
            case LATITUDE_OF_NATURAL_ORIGIN -> phi0;
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.LONGITUDE_OF_NATURAL_ORIGIN, MethodParameter.LATITUDE_OF_NATURAL_ORIGIN);
    }
}
