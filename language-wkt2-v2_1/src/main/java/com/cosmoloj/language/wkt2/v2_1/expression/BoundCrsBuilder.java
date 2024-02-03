package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforePredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import com.cosmoloj.util.function.Predicates;
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
            case 2 -> OperationCrs.SourceCrs.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> OperationCrs.TargetCrs.class::isInstance;
            case 5 -> SpecialSymbol.COMMA;
            case 6 -> Operation.AbridgedTransformation.class::isInstance;
            default -> {
                if (odd() && beyond(8)) {
                    yield RightDelimiter.class::isInstance;
                } else if (even() && beyond(7)) {
                    yield Predicates.of(Identifier.class::isInstance).or(Remark.class::isInstance);
                } else if (odd() && beyond(6)) {
                    yield Predicates.of(RightDelimiter.class::isInstance).or(SpecialSymbol.COMMA);
                }
                yield Predicates.no();
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
                    yield Predicates.of(Identifier.class::isInstance).or(Remark.class::isInstance);
                }
                yield Predicates.yes();
            }
            case 2 -> odd() && beyond(8)
                ? Predicates.of(Identifier.class::isInstance).or(Operation.AbridgedTransformation.class::isInstance)
                : Predicates.yes();
            default -> Predicates.yes();
        };
    }

    @Override
    public BoundCrs build() {

        return new BoundCrs(first(), last(), index(),
                token(2),
                token(4),
                token(6),
                tokens(Identifier.class),
                firstToken(Remark.class));
    }
}
