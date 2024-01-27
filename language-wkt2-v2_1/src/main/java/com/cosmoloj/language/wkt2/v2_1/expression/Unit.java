package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.UnsignedNumericLiteral;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class Unit extends AbstractExpression {

    private final QuotedLatinText name;
    private final UnsignedNumericLiteral conversionFactor;
    private final List<Identifier> identifiers;

    public Unit(final int start, final int end, final int index, final QuotedLatinText name,
            final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
        super(start, end, index);
        this.name = name;
        this.conversionFactor = conversionFactor;
        this.identifiers = identifiers;
    }

    public QuotedLatinText getName() {
        return this.name;
    }

    public UnsignedNumericLiteral getConversionFactor() {
        return conversionFactor;
    }

    public List<Identifier> getIdentifiers() {
        return identifiers;
    }

    public static class Angle extends Unit {

        public static final Predicate<Object> INSTANCE_OF_ANGLE = t -> t instanceof Angle;

        public Angle(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }

    public static class Length extends Unit {

        public static final Predicate<Object> INSTANCE_OF_LENGTH = t -> t instanceof Length;

        public Length(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }

    public static class Scale extends Unit {

        public static final Predicate<Object> INSTANCE_OF_SCALE = t -> t instanceof Scale;

        public Scale(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }

    public static class Parametric extends Unit {

        public static final Predicate<Object> INSTANCE_OF_PARAMETRIC = t -> t instanceof Parametric;

        public Parametric(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }

    public static class Time extends Unit {

        public static final Predicate<Object> INSTANCE_OF_TIME = t -> t instanceof Time;

        public Time(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }
}
