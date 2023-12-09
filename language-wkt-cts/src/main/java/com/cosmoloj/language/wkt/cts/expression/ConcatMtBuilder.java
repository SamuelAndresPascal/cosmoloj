package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ConcatMtBuilder extends CheckTokenBuilder<Token, ConcatMt>
        implements ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate() {
        return switch (size()) {
            case 0 -> WktName.CONCAT_MT;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> MathTransform.INSTANCE_OF;
            default -> {
                if (odd() && beyond(2)) {
                    yield SpecialSymbol.COMMA.or(RightDelimiter.INSTANCE_OF);
                } else if (even() && beyond(3)) {
                    yield MathTransform.INSTANCE_OF;
                } else {
                    yield t -> false;
                }
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        return (even() && beyond(3)) ? SpecialSymbol.COMMA : t -> true;
    }

    @Override
    public ConcatMt build() {
        return new ConcatMt(first(), last(), index(), tokens(MathTransform.INSTANCE_OF));
    }
}
