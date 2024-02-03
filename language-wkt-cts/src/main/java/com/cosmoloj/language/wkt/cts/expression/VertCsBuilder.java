package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforeIndexPredicateTokenBuilder;
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
public class VertCsBuilder extends CheckTokenBuilder<Token, VertCs>
        implements ConstraintBeforeIndexPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate() {
        return switch (size()) {
            case 0 -> WktName.VERT_CS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedName.class::isInstance;
            case 3, 5 -> SpecialSymbol.COMMA;
            case 4 -> VertDatum.class::isInstance;
            case 6 -> Unit.class::isInstance;
            case 7, 9 -> SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance);
            case 8 -> pb(Authority.class, Axis.class);
            case 10 -> Authority.class::isInstance;
            case 11 -> RightDelimiter.class::isInstance;
            default -> Predicates.no();
        };
    }

    @Override
    public Predicate<? super Token> constraintBeforeIndex(final int before, final int index) {
        return switch (before) {
            case 1 -> switch (index) {
                case 8, 10 -> SpecialSymbol.COMMA;
                case 9 -> waiting(SpecialSymbol.COMMA) ? Axis.class::isInstance : Predicates.yes();
                default -> Predicates.yes();
            };
            default -> Predicates.yes();
        };
    }

    @Override
    public VertCs build() {
        return new VertCs(first(), last(), index(),
                token(2),
                token(4),
                token(6),
                size() > 9 && token(8) instanceof Axis ? token(8) : null,
                authority());
    }

    private Authority authority() {
        if (size() == 12) {
            return token(10);
        } else {
            return size() == 10 && token(8) instanceof Authority ? token(8) : null;
        }
    }
}
