package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <D>
 */
public abstract class SimpleCrsShell<D extends AbstractExpression> extends Crs {

    private final D datum;
    private final CoordinateSystem coordinateSystem;

    public SimpleCrsShell(final int start, final int end, final int index, final QuotedLatinText name,
            final D datum, final CoordinateSystem coordinateSystem, final Scope scope,
            final List<Extent> extents, final List<Identifier> identifiers, final Remark remark) {
        super(start, end, index, name, scope, extents, identifiers, remark);
        this.datum = datum;
        this.coordinateSystem = coordinateSystem;
    }

    public D getDatum() {
        return this.datum;
    }

    public CoordinateSystem getCoordinateSystem() {
        return this.coordinateSystem;
    }

    public static class VerticalCrs extends SimpleCrsShell<NameAndAnchorDatum.VerticalDatum> {

        public static final Predicate<Object> INSTANCE_OF = t -> t instanceof VerticalCrs;

        public VerticalCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.VerticalDatum datum, final CoordinateSystem coordinateSystem,
                final Scope scope, final List<Extent> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, scope, extents, identifiers, remark);
        }
    }

    public static class EngineeringCrs extends SimpleCrsShell<NameAndAnchorDatum.EngineeringDatum>
            implements HorizontalCrs {

        public static final Predicate<Object> INSTANCE_OF = t -> t instanceof EngineeringCrs;

        public EngineeringCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.EngineeringDatum datum, final CoordinateSystem coordinateSystem,
                final Scope scope, final List<Extent> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, scope, extents, identifiers, remark);
        }
    }

    public static class ImageCrs extends SimpleCrsShell<ImageDatum> {

        public static final Predicate<Object> INSTANCE_OF = t -> t instanceof ImageCrs;

        public ImageCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final ImageDatum datum, final CoordinateSystem coordinateSystem,
                final Scope scope, final List<Extent> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, scope, extents, identifiers, remark);
        }
    }

    public static class ParametricCrs extends SimpleCrsShell<NameAndAnchorDatum.ParametricDatum> {

        public static final Predicate<Object> INSTANCE_OF = t -> t instanceof ParametricCrs;

        public ParametricCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.ParametricDatum datum, final CoordinateSystem coordinateSystem,
                final Scope scope, final List<Extent> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, scope, extents, identifiers, remark);
        }
    }

    public static class TemporalCrs extends SimpleCrsShell<NameAndAnchorDatum.TemporalDatum> {

        public static final Predicate<Object> INSTANCE_OF = t -> t instanceof TemporalCrs;

        public TemporalCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final NameAndAnchorDatum.TemporalDatum datum, final CoordinateSystem coordinateSystem,
                final Scope scope, final List<Extent> extents, final List<Identifier> identifiers,
                final Remark remark) {
            super(start, end, index, name, datum, coordinateSystem, scope, extents, identifiers, remark);
        }
    }
}
