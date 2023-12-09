package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforeIndexPredicateTokenBuilder;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
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
            case 2 -> QuotedName.QUOTED_NAME;
            case 3, 5 -> SpecialSymbol.COMMA;
            case 4 -> VertDatum.INSTANCE_OF;
            case 6 -> Unit.INSTANCE_OF_CTS;
            case 7, 9 -> SpecialSymbol.COMMA.or(RightDelimiter.INSTANCE_OF);
            case 8 -> Authority.INSTANCE_OF.or(Axis.INSTANCE_OF);
            case 10 -> Authority.INSTANCE_OF;
            case 11 -> RightDelimiter.INSTANCE_OF;
            default -> t -> false;
        };
    }

    @Override
    public Predicate<? super Token> constraintBeforeIndex(final int before, final int index) {
        return switch (before) {
            case 1 -> switch (index) {
                case 8, 10 -> SpecialSymbol.COMMA;
                case 9 -> current(SpecialSymbol.COMMA) ? Axis.INSTANCE_OF : t -> true;
                default -> t -> true;
            };
            default -> t -> true;
        };
    }

    @Override
    public VertCs build() {
        final int size = size();
        final Axis axis = size > 9 && testToken(8, Axis.INSTANCE_OF) ? token(8) : null;
        final Authority authority = size == 12 ? token(10)
                : ((size == 10 && testToken(8, Authority.INSTANCE_OF)) ? token(8) : null);
        return new VertCs(first(), last(), index(), token(2), token(4), token(6), axis, authority);
    }
}
