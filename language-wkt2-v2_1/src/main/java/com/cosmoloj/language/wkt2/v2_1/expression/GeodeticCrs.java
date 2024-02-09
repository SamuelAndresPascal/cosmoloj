package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class GeodeticCrs extends Crs {

    private final GeodeticDatum datum;
    private final SpatialCoordinateSystem coordinateSystem;

    public GeodeticCrs(final int start, final int end, final int index, final QuotedLatinText name,
            final GeodeticDatum datum, final SpatialCoordinateSystem coordinateSystem,
            final List<Usage> extents, final List<Identifier> identifiers, final Remark remark) {
        super(start, end, index, name, extents, identifiers, remark);
        this.datum = datum;
        this.coordinateSystem = coordinateSystem;
    }

    public GeodeticDatum getDatum() {
        return this.datum;
    }

    public SpatialCoordinateSystem getCoordinateSystem() {
        return this.coordinateSystem;
    }

    public static class Geographic2DCrs extends GeodeticCrs implements HorizontalCrs {

        public Geographic2DCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final GeodeticDatum datum, final SpatialCoordinateSystem.Ellipsoidal2DCoordinateSystem coordinateSystem,
                final List<Usage> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, extents, identifiers, remark);
        }
    }
}
