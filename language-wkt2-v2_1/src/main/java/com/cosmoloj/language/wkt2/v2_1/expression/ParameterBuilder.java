package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
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
                    case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
                    case 3 -> SpecialSymbol.comma;
                    case 4 -> SignedNumericLiteral.INSTANCE_OF;
                    case 5 -> RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
                    case 6 -> Identifier.INSTANCE_OF
                            .or(Unit.Angle.INSTANCE_OF_ANGLE)
                            .or(Unit.Length.INSTANCE_OF_LENGTH)
                            .or(Unit.Scale.INSTANCE_OF_SCALE);
                    default -> {
                        if (odd()) {
                            yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
                        } else {
                            yield Identifier.INSTANCE_OF;
                        }
                    }
                };
            }

            @Override
            public Parameter build() {

                final Unit unit = (size() >= 8
                        && testToken(6, Unit.Length.INSTANCE_OF_LENGTH.or(Unit.Angle.INSTANCE_OF_ANGLE)
                                .or(Unit.Scale.INSTANCE_OF_SCALE)))
                        ? token(6)
                        : null;

                return new Parameter(first(), last(), index(), token(2), token(4), unit,
                        tokens(Identifier.INSTANCE_OF));
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
                    case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
                    case 3 -> SpecialSymbol.comma;
                    case 4 -> SignedNumericLiteral.INSTANCE_OF;
                    case 5 -> RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
                    case 6 -> Unit.Angle.INSTANCE_OF_ANGLE
                            .or(Unit.Length.INSTANCE_OF_LENGTH)
                            .or(Unit.Parametric.INSTANCE_OF_PARAMETRIC)
                            .or(Unit.Scale.INSTANCE_OF_SCALE)
                            .or(Unit.Time.INSTANCE_OF_TIME);
                    default -> {
                        if (odd()) {
                            yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
                        } else {
                            yield Identifier.INSTANCE_OF;
                        }
                    }
                };
            }

            @Override
            public Parameter build() {
                return new Parameter(first(), last(), index(), token(2), token(4), token(6),
                        tokens(Identifier.INSTANCE_OF));
            }
        };
    }
}
