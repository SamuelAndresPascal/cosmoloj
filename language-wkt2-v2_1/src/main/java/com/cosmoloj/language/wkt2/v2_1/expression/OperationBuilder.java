package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.builder.TokenBuilder;
import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforePredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 * @param <O>
 * @param <M>
 * @param <P>
 */
public interface OperationBuilder<O extends Operation<M, P>, M extends Method, P extends AbstractParam>
        extends TokenBuilder<Token, O>, PredicateIndexTokenBuilder<Token> {

    class MapProjectionBuilder extends CheckTokenBuilder<Token, Operation.MapProjection>
            implements OperationBuilder<Operation.MapProjection, Method.MapProjectionMethod, Parameter>,
            ConstraintBeforePredicateTokenBuilder<Token> {

        @Override
        public Predicate<? super Token> predicate(final int currentIndex) {
            if (currentIndex == 0) {
                return WktKeyword.CONVERSION.or(Method.MapProjectionMethod.INSTANCE_OF_MAP_PROJECTION_METHOD);
            }

            if (Method.MapProjectionMethod.INSTANCE_OF_MAP_PROJECTION_METHOD.test(token(0))) {
                if (odd()) {
                    return SpecialSymbol.COMMA;
                } else {
                    return Parameter.INSTANCE_OF.or(Identifier.INSTANCE_OF);
                }
            }

            return switch (currentIndex) {
                case 1 -> LeftDelimiter.class::isInstance;
                case 2 -> QuotedLatinText.class::isInstance;
                case 3 -> SpecialSymbol.COMMA;
                case 4 -> Method.MapProjectionMethod.INSTANCE_OF_MAP_PROJECTION_METHOD;
                default -> odd() ? RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA)
                    : Parameter.INSTANCE_OF.or(Identifier.INSTANCE_OF);
            };
        }

        @Override
        public Predicate<? super Token> constraintBefore(final int before) {
            if (even() && beyond(4)) {
                return switch (before) {
                    case 1 -> SpecialSymbol.COMMA;
                    case 2 -> Parameter.INSTANCE_OF.or(
                            current(Parameter.INSTANCE_OF)
                                    ? Method.MapProjectionMethod.INSTANCE_OF_MAP_PROJECTION_METHOD
                                    : Identifier.INSTANCE_OF);
                    default -> t -> true;
                };
            }
            return t -> true;
        }

        @Override
        public Operation.MapProjection build() {

            return new Operation.MapProjection(first(), last(), index(),
                    firstToken(QuotedLatinText.class::isInstance),
                    firstToken(Method.MapProjectionMethod.INSTANCE_OF_MAP_PROJECTION_METHOD),
                    tokens(AbstractParam.INSTANCE_OF),
                    tokens(Identifier.class::isInstance));
        }
    }

    class DerivingConversionBuilder extends CheckTokenBuilder<Token, Operation.DerivingConversion>
            implements OperationBuilder<Operation.DerivingConversion, Method.OperationMethod, AbstractParam>,
            ConstraintBeforePredicateTokenBuilder<Token> {

        @Override
        public Predicate<? super Token> predicate(final int currentIndex) {
            return switch (currentIndex) {
                case 0 -> WktKeyword.DERIVINGCONVERSION;
                case 1 -> LeftDelimiter.class::isInstance;
                case 2 -> QuotedLatinText.class::isInstance;
                case 3 -> SpecialSymbol.COMMA;
                case 4 -> Method.OperationMethod.INSTANCE_OF_OPERATION_METHOD;
                default -> {
                    if (odd()) {
                        yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                    } else {
                        yield Parameter.INSTANCE_OF.or(Identifier.class::isInstance);
                    }
                }
            };
        }

        @Override
        public Predicate<? super Token> constraintBefore(final int before) {
            if (even() && beyond(4)) {
                return switch (before) {
                    case 1 -> SpecialSymbol.COMMA;
                    case 2 -> Parameter.INSTANCE_OF.or(current(Parameter.INSTANCE_OF)
                            ? Method.OperationMethod.INSTANCE_OF_OPERATION_METHOD : Identifier.class::isInstance);
                    default -> t -> true;
                };
            }
            return t -> true;
        }

        @Override
        public Operation.DerivingConversion build() {

            return new Operation.DerivingConversion(first(), last(), index(), token(2), token(4),
                    tokens(AbstractParam.INSTANCE_OF),
                    tokens(Identifier.class::isInstance));
        }
    }
}
