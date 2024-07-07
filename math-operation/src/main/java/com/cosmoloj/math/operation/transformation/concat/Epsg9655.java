package com.cosmoloj.math.operation.transformation.concat;

import com.cosmoloj.bibliography.cosmoloj.Cosmoloj;
import com.cosmoloj.format.gr3df97a.GridGr3df97a;
import com.cosmoloj.math.operation.AutoInverse;
import com.cosmoloj.math.operation.MethodParameter;
import com.cosmoloj.math.operation.conversion.Epsg9602;
import com.cosmoloj.math.operation.conversion.Epsg9659;
import com.cosmoloj.math.operation.surface.Ellipsoid;
import com.cosmoloj.math.operation.transformation.Epsg1031;
import com.cosmoloj.math.operation.transformation.InversibleTransformation;
import com.cosmoloj.math.tabular.core.DoubleTabulars;
import com.cosmoloj.util.bib.Reference;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * <div>EPSG::9655</div>
 * <div class="en">France geocentric interpolation</div>
 *
 * @author Samuel Andrés
 *
 * @see Epsg9659
 * @see Epsg9602
 * @see Epsg1031
 * @see GridGr3df97a
 */
@Reference(Cosmoloj.IOGP_GUIDANCE_NOTE_7_2_2019)
public class Epsg9655 implements InversibleTransformation<double[], double[]>, AutoInverse<double[]> {

    private final Epsg9659 ntf3d2d; // 3d => 2d
    private final Epsg9602 ntfGeogGeoc; // geog => geoc
    private final Epsg9602 rgf93GeogGeoc; // geog => geoc
    private final Epsg1031 geocTranslation; // core geoc to geoc of operation 1651
    private final Path gridFile;
    private final GridGr3df97a grid;

    public Epsg9655(final Ellipsoid ntf, final Ellipsoid rgf93, final Path gridFile) throws IOException {
        this(ntf, 0., rgf93, gridFile);
    }

    // from ntf (2d) to rgf93 (3d)
    public Epsg9655(final Ellipsoid ntf, final double ntfInverseHeight, final Ellipsoid rgf93, final Path gridFile)
            throws IOException {
        this.ntf3d2d = new Epsg9659(ntf, ntfInverseHeight);
        this.ntfGeogGeoc = new Epsg9602(ntf);
        this.rgf93GeogGeoc = new Epsg9602(rgf93);
        this.geocTranslation = new Epsg1031(-168., -60., 320.);
        this.gridFile = gridFile;
        this.grid = GridGr3df97a.read(gridFile);
    }

    public static Epsg9655 ofParams(final Ellipsoid source, final Ellipsoid target,
            final Map<MethodParameter, ?> params) {
        try {
        return new Epsg9655(source, target,
            (Path) params.get(MethodParameter.GEOCENTRIC_TRANSLATION_FILE));
        } catch (final IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    @Override // ntf to rgf93
    public double[] compute(final double[] input) {
        final double[] geog3dNtf = ntf3d2d.inverse(input); // 2d to 3d
        final double[] geocNtf = ntfGeogGeoc.compute(geog3dNtf); // geog to geoc
        final double[] approxGeocRgf93 = geocTranslation.compute(geocNtf);
        // should theoretically be interative
        final double[] approxGeogRgf93 = rgf93GeogGeoc.inverse(approxGeocRgf93);
        final double[] interpol = this.grid.apply(
                new double[]{Math.toDegrees(approxGeogRgf93[1]), Math.toDegrees(approxGeogRgf93[0])});
        final double[] geocRgf93 = DoubleTabulars.add(geocNtf, interpol);
        return rgf93GeogGeoc.inverse(geocRgf93); // to rgf (3d)
    }

    @Override
    public double[] inverse(final double[] input) {
        final double[] interpol = this.grid.apply(new double[]{Math.toDegrees(input[1]), Math.toDegrees(input[0])});
        final double[] geocRgf93 = rgf93GeogGeoc.compute(input); // geog to geoc
        final double[] geocNtf = DoubleTabulars.minus(geocRgf93, interpol);
        final double[] geog3dNtf = ntfGeogGeoc.inverse(geocNtf);
        return ntf3d2d.compute(geog3dNtf);
    }

    @Override
    public List<MethodParameter> getParameters() {
        return List.of(MethodParameter.GEOCENTRIC_TRANSLATION_FILE);
    }

    @Override
    public Object getParameter(final MethodParameter parameter) {
        if (MethodParameter.GEOCENTRIC_TRANSLATION_FILE.equals(parameter)) {
            return gridFile;
        }
        throw new IllegalArgumentException();
    }
}
