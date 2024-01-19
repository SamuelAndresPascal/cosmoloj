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
            case 4 -> LocalDatum.INSTANCE_OF;
            case 6 -> Unit.INSTANCE_OF_CTS;
            case 8 -> Axis.INSTANCE_OF;
            default -> {
                if (odd() && beyond(8)) {
                    yield SpecialSymbol.COMMA.or(RightDelimiter.INSTANCE_OF);
                } else if (even() && beyond(9)) {
                    yield Authority.INSTANCE_OF.or(Axis.INSTANCE_OF);
                }
                yield Predicates.no();
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        if (odd() && beyond(8)) {
            if (current(RightDelimiter.INSTANCE_OF)) {
                return Axis.INSTANCE_OF.or(Authority.INSTANCE_OF);
            } else if (current(SpecialSymbol.COMMA)) {
                return Axis.INSTANCE_OF;
            }
        } else if (even() && beyond(9)) {
            return SpecialSymbol.COMMA;
        }
        return Predicates.yes();
    }

    @Override
    public LocalCs build() {

        return new LocalCs(first(), last(), index(), token(2), token(4), token(6), tokens(Axis.INSTANCE_OF),
                firstToken(Authority.INSTANCE_OF));
    }
}
