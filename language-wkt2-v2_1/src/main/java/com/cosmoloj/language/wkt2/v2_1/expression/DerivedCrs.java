package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 * @param <B>
 * @param <O>
 * @param <M>
 * @param <P>
 */
public abstract class DerivedCrs
        <B extends BaseCrs, O extends Operation<M, P>, M extends Method, P extends AbstractParam> extends Crs {

    private final B baseCrs;
    private final O operation;
    private final SpatialCoordinateSystem coordinateSystem;

    protected DerivedCrs(final int start, final int end, final int index, final QuotedLatinText name,
            final B baseCrs, final O operation, final SpatialCoordinateSystem coordinateSystem,
            final List<Usage> usages, final List<Identifier> identifiers, final Remark remark) {
        super(start, end, index, name, usages, identifiers, remark);
        this.baseCrs = baseCrs;
        this.operation = operation;
        this.coordinateSystem = coordinateSystem;
    }

    public B getBaseCrs() {
        return this.baseCrs;
    }

    public O getOperation() {
        return this.operation;
    }

    public SpatialCoordinateSystem getCoordinateSystem() {
        return this.coordinateSystem;
    }

    public static class ProjectedCrs
            extends DerivedCrs<BaseGeodeticCrs, Operation.MapProjection, Method.MapProjectionMethod, Parameter>
            implements HorizontalCrs {

        public ProjectedCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final BaseGeodeticCrs baseCrs, final Operation.MapProjection projection,
                final SpatialCoordinateSystem coordinateSystem, final List<Usage> extents,
                final List<Identifier> identifiers, final Remark remark) {
            super(start, end, index, name, baseCrs, projection, coordinateSystem, extents, identifiers, remark);
        }
    }

    public static class DerivedGeodeticCrs
            extends DerivedCrs<BaseGeodeticCrs, Operation.DerivingConversion, Method.OperationMethod, AbstractParam> {

        public DerivedGeodeticCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final BaseGeodeticCrs baseCrs, final Operation.DerivingConversion conversion,
                final SpatialCoordinateSystem coordinateSystem, final List<Usage> extents,
                final List<Identifier> identifiers, final Remark remark) {
            super(start, end, index, name, baseCrs, conversion, coordinateSystem, extents, identifiers, remark);
        }
    }

    public static class DerivedVerticalCrs extends DerivedCrs<BaseCrs.BaseVerticalCrs, Operation.DerivingConversion,
            Method.OperationMethod, AbstractParam> {

        public DerivedVerticalCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final BaseCrs.BaseVerticalCrs baseCrs, final Operation.DerivingConversion conversion,
                final SpatialCoordinateSystem coordinateSystem, final List<Usage> extents,
                final List<Identifier> identifiers, final Remark remark) {
            super(start, end, index, name, baseCrs, conversion, coordinateSystem, extents, identifiers, remark);
        }
    }

    public static class DerivedEngineeringCrs<B extends BaseCrs> extends DerivedCrs<B, Operation.DerivingConversion,
            Method.OperationMethod, AbstractParam> {

        public DerivedEngineeringCrs(final int start, final int end, final int index,
                final QuotedLatinText name,
                final B baseCrs,
                final Operation.DerivingConversion conversion,
                final SpatialCoordinateSystem coordinateSystem,
                final List<Usage> extents, final List<Identifier> identifiers, final Remark remark) {
            super(start, end, index, name, baseCrs, conversion, coordinateSystem, extents, identifiers, remark);
        }
    }

    public static class DerivedParametricCrs
            extends DerivedCrs<BaseCrs.BaseParametricCrs, Operation.DerivingConversion, Method.OperationMethod,
            AbstractParam> {

        public DerivedParametricCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final BaseCrs.BaseParametricCrs baseCrs, final Operation.DerivingConversion conversion,
                final SpatialCoordinateSystem coordinateSystem, final List<Usage> extents,
                final List<Identifier> identifiers, final Remark remark) {
            super(start, end, index, name, baseCrs, conversion, coordinateSystem, extents, identifiers, remark);
        }
    }

    public static class DerivedTemporalCrs extends DerivedCrs<BaseCrs.BaseTemporalCrs, Operation.DerivingConversion,
            Method.OperationMethod, AbstractParam> {

        public DerivedTemporalCrs(final int start, final int end, final int index, final QuotedLatinText name,
                final BaseCrs.BaseTemporalCrs baseCrs, final Operation.DerivingConversion conversion,
                final SpatialCoordinateSystem coordinateSystem, final List<Usage> extents,
                final List<Identifier> identifiers, final Remark remark) {
            super(start, end, index, name, baseCrs, conversion, coordinateSystem, extents, identifiers, remark);
        }
    }
}
