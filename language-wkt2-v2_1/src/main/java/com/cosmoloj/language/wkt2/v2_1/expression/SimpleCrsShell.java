package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 * @param <D>
 */
public abstract class SimpleCrsShell<D extends AbstractExpression> extends Crs {

    private final D datum;
    private final SpatialCoordinateSystem coordinateSystem;

    protected SimpleCrsShell(final int start, final int end, final int index, final QuotedLatinText name,
            final D datum, final SpatialCoordinateSystem coordinateSystem,
            final List<Usage> extents, final List<Identifier> identifiers, final Remark remark) {
        super(start, end, index, name, extents, identifiers, remark);
        this.datum = datum;
        this.coordinateSystem = coordinateSystem;
    }

    public D getDatum() {
        return this.datum;
    }

    public SpatialCoordinateSystem getCoordinateSystem() {
        return this.coordinateSystem;
    }

    public static class VerticalCrs extends SimpleCrsShell<NameAndAnchorDatum.VerticalDatum> {

        public VerticalCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.VerticalDatum datum, final SpatialCoordinateSystem coordinateSystem,
                final List<Usage> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, extents, identifiers, remark);
        }
    }

    public static class EngineeringCrs extends SimpleCrsShell<NameAndAnchorDatum.EngineeringDatum>
            implements HorizontalCrs {

        public EngineeringCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.EngineeringDatum datum, final SpatialCoordinateSystem coordinateSystem,
                final List<Usage> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, extents, identifiers, remark);
        }
    }

    public static class ImageCrs extends SimpleCrsShell<ImageDatum> {

        public ImageCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final ImageDatum datum, final SpatialCoordinateSystem coordinateSystem,
                final List<Usage> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, extents, identifiers, remark);
        }
    }

    public static class ParametricCrs extends SimpleCrsShell<NameAndAnchorDatum.ParametricDatum> {

        public ParametricCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.ParametricDatum datum, final SpatialCoordinateSystem coordinateSystem,
                final List<Usage> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, extents, identifiers, remark);
        }
    }

    public static class TemporalCrs extends SimpleCrsShell<NameAndAnchorDatum.TemporalDatum> {

        public TemporalCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.TemporalDatum datum, final SpatialCoordinateSystem coordinateSystem,
                final List<Usage> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, extents, identifiers, remark);
        }
    }
}
