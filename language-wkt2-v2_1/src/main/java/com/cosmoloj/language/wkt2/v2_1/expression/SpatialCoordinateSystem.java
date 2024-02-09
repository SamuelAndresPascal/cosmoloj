package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.CsType;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class SpatialCoordinateSystem extends AbstractExpression {

    private final EnumLexeme<CsType> type;
    private final UnsignedInteger dimension;
    private final List<Identifier> identifiers;
    private final List<SpatialTemporalAxis> axis;
    private final Unit unit;

    public SpatialCoordinateSystem(final int start, final int end, final int index, final EnumLexeme<CsType> type,
            final UnsignedInteger dimension, final List<Identifier> identifiers, final List<SpatialTemporalAxis> axis,
            final Unit unit) {
        super(start, end, index);
        this.type = type;
        this.dimension = dimension;
        this.identifiers = identifiers;
        this.axis = axis;
        this.unit = unit;
    }

    public EnumLexeme<CsType> getType() {
        return this.type;
    }

    public UnsignedInteger getDimension() {
        return this.dimension;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public List<SpatialTemporalAxis> getAxis() {
        return this.axis;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public static class Ellipsoidal2DCoordinateSystem extends SpatialCoordinateSystem {

        public Ellipsoidal2DCoordinateSystem(final int start, final int end, final int index,
                final EnumLexeme<CsType> type, final UnsignedInteger dimension, final List<Identifier> identifiers,
                final List<SpatialTemporalAxis> axis, final Unit unit) {
            super(start, end, index, type, dimension, identifiers, axis, unit);
        }
    }
}
