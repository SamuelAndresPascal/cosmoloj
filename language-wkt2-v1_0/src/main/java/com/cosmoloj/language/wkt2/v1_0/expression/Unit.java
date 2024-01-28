package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.common.impl.semantic.AbstractExpression;
import com.cosmoloj.language.common.number.lexeme.compound.UnsignedNumericLiteral;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import java.util.List;

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

        public Angle(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }

    public static class Length extends Unit {

        public Length(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }

    public static class Scale extends Unit {

        public Scale(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }

    public static class Parametric extends Unit {

        public Parametric(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }

    public static class Time extends Unit {

        public Time(final int start, final int end, final int index, final QuotedLatinText name,
                final UnsignedNumericLiteral conversionFactor, final List<Identifier> identifiers) {
            super(start, end, index, name, conversionFactor, identifiers);
        }
    }
}
