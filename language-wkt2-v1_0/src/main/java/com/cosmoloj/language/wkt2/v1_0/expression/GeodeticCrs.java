package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class GeodeticCrs extends Crs {

    private final GeodeticDatum datum;
    private final CoordinateSystem coordinateSystem;

    public GeodeticCrs(final int start, final int end, final int index, final QuotedLatinText name,
            final GeodeticDatum datum, final CoordinateSystem coordinateSystem, final Scope scope,
            final List<Extent> extents, final List<Identifier> identifiers, final Remark remark) {
        super(start, end, index, name, scope, extents, identifiers, remark);
        this.datum = datum;
        this.coordinateSystem = coordinateSystem;
    }

    public GeodeticDatum getDatum() {
        return this.datum;
    }

    public CoordinateSystem getCoordinateSystem() {
        return this.coordinateSystem;
    }

    public static class Geographic2DCrs extends GeodeticCrs implements HorizontalCrs {

        public Geographic2DCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final GeodeticDatum datum, final CoordinateSystem.Ellipsoidal2DCoordinateSystem coordinateSystem,
                final Scope scope, final List<Extent> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, scope, extents, identifiers, remark);
        }
    }
}
