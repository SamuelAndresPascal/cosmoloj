package com.cosmoloj.math.operation.projection;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.util.bib.Reference;
import java.util.List;

/**
 * <div>EPSG::9825</div>
 * <div class="en">Pseudo Plate Carree</div>
 * <div class="en">Used only for depiction of graticule (latitude/longitude) coordinates on a computer display.
 * The axes units are decimal degrees and of variable scale. The origin is at Lat = 0, Long = 0.
 * See Equidistant Cylindrical, code 1029, for proper Plate Carrée.
 * </div>
 *
 * @see Epsg1029
 *
 * @author Samuel Andrés
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9825 implements InvertibleProjection {

    private static final int PHI = 0;
    private static final int LAMBDA = 1;
    private static final int EASTING = 0;
    private static final int NORTHING = 1;

    private final Ellipsoid ellipsoid;

    public Epsg9825(final Ellipsoid ellipsoid) {
        this.ellipsoid = ellipsoid;
    }

    public static Epsg9825 ofParams(final Ellipsoid ellipsoid) {
        return new Epsg9825(ellipsoid);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        throw new IllegalArgumentException();
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of();
    }

    @Override
    public double[] compute(final double[] input) {

        return new double[]{input[LAMBDA], input[PHI]};
     }

    @Override
    public double[] inverse(final double[] input) {

        return new double[]{input[NORTHING], input[EASTING]};
    }

    @Override
    public Ellipsoid getSurface() {
        return ellipsoid;
    }
}
