package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.CsType;
import java.util.List;

/**
 *
 * @author Samuel Andrés
 */
public class CoordinateSystem extends AbstractExpression {

    private final CsType.Lexeme type;
    private final UnsignedInteger dimension;
    private final List<Identifier> identifiers;
    private final List<Axis> axis;
    private final Unit unit;

    public CoordinateSystem(final int start, final int end, final int index, final CsType.Lexeme type,
            final UnsignedInteger dimension, final List<Identifier> identifiers, final List<Axis> axis,
            final Unit unit) {
        super(start, end, index);
        this.type = type;
        this.dimension = dimension;
        this.identifiers = identifiers;
        this.axis = axis;
        this.unit = unit;
    }

    public CsType.Lexeme getType() {
        return this.type;
    }

    public UnsignedInteger getDimension() {
        return this.dimension;
    }

    public List<Identifier> getIdentifiers() {
        return this.identifiers;
    }

    public List<Axis> getAxis() {
        return this.axis;
    }

    public Unit getUnit() {
        return this.unit;
    }

    public static class Ellipsoidal2DCoordinateSystem extends CoordinateSystem {

        public Ellipsoidal2DCoordinateSystem(final int start, final int end, final int index, final CsType.Lexeme type,
                final UnsignedInteger dimension, final List<Identifier> identifiers, final List<Axis> axis,
                final Unit unit) {
            super(start, end, index, type, dimension, identifiers, axis, unit);
        }
    }
}
