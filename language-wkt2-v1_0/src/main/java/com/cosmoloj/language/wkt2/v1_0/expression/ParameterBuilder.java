package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public abstract class ParameterBuilder extends CheckTokenBuilder<Token, Parameter>
        implements PredicateIndexTokenBuilder<Token> {

    /**
     * <div class="en">Projection parameter builder: unit is optional.</div>
     * @return <span class="en">projection parameter builder</span>
     */
    public static ParameterBuilder projectionParameter() {
        return new ParameterBuilder() {

            @Override
            public Predicate<? super Token> predicate(final int currentIndex) {
                return switch (currentIndex) {
                    case 0 -> WktKeyword.PARAMETER;
                    case 1 -> LeftDelimiter.class::isInstance;
                    case 2 -> QuotedLatinText.class::isInstance;
                    case 3 -> SpecialSymbol.COMMA;
                    case 4 -> SignedNumericLiteral.class::isInstance;
                    case 5 -> pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
                    case 6 -> pb(Identifier.class, Unit.Angle.class, Unit.Length.class, Unit.Scale.class);
                    default -> odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA)
                        : Identifier.class::isInstance;
                };
            }

            @Override
            public Parameter build() {

                final Unit unit = (size() >= 8
                        && testToken(6, pb(Unit.Length.class, Unit.Angle.class, Unit.Scale.class)))
                        ? token(6)
                        : null;

                return new Parameter(first(), last(), index(), token(2), token(4), unit,
                        tokens(Identifier.class));
            }
        };
    }

    /**
     * <div class="en">Deriving operation and coordinate operation parameter builder: unit is mandatory.</div>
     * @return <span class="en">operation parameter builder</span>
     */
    public static ParameterBuilder operationParameter() {
        return new ParameterBuilder() {

            @Override
            public Predicate<? super Token> predicate(final int currentIndex) {
                return switch (currentIndex) {
                    case 0 -> WktKeyword.PARAMETER;
                    case 1 -> LeftDelimiter.class::isInstance;
                    case 2 -> QuotedLatinText.class::isInstance;
                    case 3 -> SpecialSymbol.COMMA;
                    case 4 -> SignedNumericLiteral.class::isInstance;
                    case 5 -> pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
                    case 6 -> pb(Unit.Angle.class,
                            Unit.Length.class,
                            Unit.Parametric.class,
                            Unit.Scale.class,
                            Unit.Time.class);
                    default -> odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA)
                        : Identifier.class::isInstance;
                };
            }

            @Override
            public Parameter build() {
                return new Parameter(first(), last(), index(), token(2), token(4), token(6),
                        tokens(Identifier.class));
            }
        };
    }
}
