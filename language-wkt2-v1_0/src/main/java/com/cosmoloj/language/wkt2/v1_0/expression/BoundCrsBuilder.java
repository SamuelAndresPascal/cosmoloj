package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforePredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class BoundCrsBuilder extends CheckTokenBuilder<Token, BoundCrs> implements PredicateIndexTokenBuilder<Token>,
        ConstraintBeforePredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.BOUNDCRS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> OperationCrs.SourceCrs.INSTANCE_OF_SOURCE_CRS;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> OperationCrs.TargetCrs.INSTANCE_OF_TARGET_CRS;
            case 5 -> SpecialSymbol.COMMA;
            case 6 -> Operation.AbridgedTransformation.INSTANCE_OF_ABRIDGED_TRANSFORMATION;
            default -> {
                if (odd() && beyond(8)) {
                    yield RightDelimiter.INSTANCE_OF;
                } else if (even() && beyond(7)) {
                    yield Identifier.INSTANCE_OF.or(Remark.INSTANCE_OF);
                } else if (odd() && beyond(6)) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                }
                yield t -> false;
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintBefore(final int before) {
        return switch (before) {
            case 1 -> {
                if (even() && beyond(7)) {
                    yield SpecialSymbol.COMMA;
                } else if (odd() && beyond(8)) {
                    yield Identifier.INSTANCE_OF.or(Remark.INSTANCE_OF);
                }
                yield t -> true;
            }
            case 2 -> {
                if (odd() && beyond(8)) {
                    yield Identifier.INSTANCE_OF
                            .or(Operation.AbridgedTransformation.INSTANCE_OF_ABRIDGED_TRANSFORMATION);
                }
                yield t -> true;
            }
            default -> t -> true;
        };
    }

    @Override
    public BoundCrs build() {

        return new BoundCrs(first(), last(), index(), token(2), token(4), token(6),
                tokens(Identifier.INSTANCE_OF), firstToken(Remark.INSTANCE_OF));
    }
}
