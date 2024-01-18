package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.UnsignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <U>
 */
public class UnitBuilder<U extends Token> extends CheckTokenBuilder<Token, U>
        implements PredicateIndexTokenBuilder<Token> {

    private final Predicate<? super Token> labels;

    public UnitBuilder(final WktKeyword... labels) {
        if (labels.length == 0) {
            this.labels = WktKeyword.UNIT;
        } else {
            Predicate<? super Token> l = labels[0];
            for (int i = 1; i < labels.length; i++) {
                l = l.or(labels[i]);
            }
            this.labels = l;
        }
    }

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> labels;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> UnsignedNumericLiteral.INSTANCE_OF;
            default -> odd() ? RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA) : Identifier.INSTANCE_OF;

        };
    }

    @Override
    public U build() {
        return (U) new Unit(first(), last(), index(), token(2), token(4), tokens(Identifier.INSTANCE_OF));
    }

    public static class AngleUnitBuilder extends UnitBuilder<Unit.Angle> {

        public AngleUnitBuilder() {
            super(WktKeyword.ANGLEUNIT, WktKeyword.UNIT);
        }

        @Override
        public Unit.Angle build() {
            return new Unit.Angle(first(), last(), index(), token(2), token(4), tokens(Identifier.INSTANCE_OF));
        }
    }

    public static class LengthUnitBuilder extends UnitBuilder<Unit.Length> {

        public LengthUnitBuilder() {
            super(WktKeyword.LENGTHUNIT, WktKeyword.UNIT);
        }

        @Override
        public Unit.Length build() {
            return new Unit.Length(first(), last(), index(), token(2), token(4), tokens(Identifier.INSTANCE_OF));
        }
    }

    public static class ScaleUnitBuilder extends UnitBuilder<Unit.Scale> {

        public ScaleUnitBuilder() {
            super(WktKeyword.SCALEUNIT, WktKeyword.UNIT);
        }

        @Override
        public Unit.Scale build() {
            return new Unit.Scale(first(), last(), index(), token(2), token(4), tokens(Identifier.INSTANCE_OF));
        }
    }

    public static class ParametricUnitBuilder extends UnitBuilder<Unit.Parametric> {

        public ParametricUnitBuilder() {
            super(WktKeyword.PARAMETRICUNIT);
        }

        @Override
        public Unit.Parametric build() {
            return new Unit.Parametric(first(), last(), index(), token(2), token(4), tokens(Identifier.INSTANCE_OF));
        }
    }

    public static class TimeUnitBuilder extends UnitBuilder<Unit.Time> {

        public TimeUnitBuilder() {
            super(WktKeyword.TIMEUNIT);
        }

        @Override
        public Unit.Time build() {
            return new Unit.Time(first(), last(), index(), token(2), token(4), tokens(Identifier.INSTANCE_OF));
        }
    }
}
