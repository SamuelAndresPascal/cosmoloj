package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.expression.Parameter;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.util.function.Predicates;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ParamMtBuilder extends CheckTokenBuilder<Token, ParamMt>
        implements ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate() {
        return switch (size()) {
            case 0 -> WktName.PARAM_MT;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedName.class::isInstance;
            case 3 -> SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance);
            default -> {
                if (even() && beyond(3)) {
                    yield Parameter.class::isInstance;
                } else if (odd() && beyond(4)) {
                    yield SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance);
                }
                yield Predicates.no();
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        if (even() && beyond(3)) {
            return SpecialSymbol.COMMA;
        } else if (odd() && beyond(4)) {
            return Parameter.class::isInstance;
        }
        return t -> true;
    }

    @Override
    public ParamMt build() {
        return new ParamMt(first(), last(), index(), token(2), tokens(Parameter.class::isInstance));
    }
}
