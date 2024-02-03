package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.UnsignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
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
        this.labels = labels.length == 0 ? WktKeyword.UNIT : pb(labels[0], labels);
    }

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> labels;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> UnsignedNumericLiteral.class::isInstance;
            default -> odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA) : Identifier.class::isInstance;
        };
    }

    @Override
    public U build() {
        return (U) new Unit(first(), last(), index(), token(2), token(4), tokens(Identifier.class));
    }

    public static class AngleUnitBuilder extends UnitBuilder<Unit.Angle> {

        public AngleUnitBuilder() {
            super(WktKeyword.ANGLEUNIT, WktKeyword.UNIT);
        }

        @Override
        public Unit.Angle build() {
            return new Unit.Angle(first(), last(), index(), token(2), token(4), tokens(Identifier.class));
        }
    }

    public static class LengthUnitBuilder extends UnitBuilder<Unit.Length> {

        public LengthUnitBuilder() {
            super(WktKeyword.LENGTHUNIT, WktKeyword.UNIT);
        }

        @Override
        public Unit.Length build() {
            return new Unit.Length(first(), last(), index(), token(2), token(4), tokens(Identifier.class));
        }
    }

    public static class ScaleUnitBuilder extends UnitBuilder<Unit.Scale> {

        public ScaleUnitBuilder() {
            super(WktKeyword.SCALEUNIT, WktKeyword.UNIT);
        }

        @Override
        public Unit.Scale build() {
            return new Unit.Scale(first(), last(), index(), token(2), token(4), tokens(Identifier.class));
        }
    }

    public static class ParametricUnitBuilder extends UnitBuilder<Unit.Parametric> {

        public ParametricUnitBuilder() {
            super(WktKeyword.PARAMETRICUNIT);
        }

        @Override
        public Unit.Parametric build() {
            return new Unit.Parametric(
                    first(), last(), index(), token(2), token(4), tokens(Identifier.class));
        }
    }

    public static class TimeUnitBuilder extends CheckTokenBuilder<Token, Unit.Time>
        implements PredicateIndexTokenBuilder<Token> {

        @Override
        public Predicate<? super Token> predicate(final int currentIndex) {
            return switch (currentIndex) {
                case 0 -> WktKeyword.TIMEUNIT.or(WktKeyword.TEMPORALQUANTITY);
                case 1 -> LeftDelimiter.class::isInstance;
                case 2 -> QuotedLatinText.class::isInstance;
                case 3 -> SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance);
                case 4 -> pb(RightDelimiter.class, UnsignedNumericLiteral.class, Identifier.class);
                default -> odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA) : Identifier.class::isInstance;
            };
        }

        @Override
        public Unit.Time build() {
            return new Unit.Time(first(), last(), index(),
                    token(2),
                    firstToken(UnsignedNumericLiteral.class),
                    tokens(Identifier.class));
        }
    }
}
