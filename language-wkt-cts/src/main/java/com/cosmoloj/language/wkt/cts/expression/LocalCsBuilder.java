package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
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
public class LocalCsBuilder extends CheckTokenBuilder<Token, LocalCs>
        implements ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate() {
        return switch (size()) {
            case 0 -> WktName.LOCAL_CS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedName.class::isInstance;
            case 3, 5, 7 -> SpecialSymbol.COMMA;
            case 4 -> LocalDatum.class::isInstance;
            case 6 -> Unit.class::isInstance;
            case 8 -> Axis.class::isInstance;
            default -> {
                if (odd() && beyond(8)) {
                    yield SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance);
                } else if (even() && beyond(9)) {
                    yield pb(Authority.class, Axis.class);
                }
                yield Predicates.no();
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        if (odd() && beyond(8)) {
            if (waiting(RightDelimiter.class)) {
                return pb(Axis.class, Authority.class);
            } else if (waiting(SpecialSymbol.COMMA)) {
                return Axis.class::isInstance;
            }
        } else if (even() && beyond(9)) {
            return SpecialSymbol.COMMA;
        }
        return Predicates.yes();
    }

    @Override
    public LocalCs build() {

        return new LocalCs(first(), last(), index(),
                token(2),
                token(4),
                token(6),
                tokens(Axis.class),
                firstToken(Authority.class));
    }
}
