package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.util.function.Predicates;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ToWgs84Builder extends CheckTokenBuilder<Token, ToWgs84> implements PredicateIndexTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int index) {
        return switch (index) {
            case 0 -> WktName.TOWGS84;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2, 4, 6, 8, 10, 12, 14 -> SignedNumericLiteral.class::isInstance;
            case 3, 5, 7, 9, 11, 13 -> SpecialSymbol.COMMA;
            case 15 -> RightDelimiter.class::isInstance;
            default -> Predicates.no();
        };
    }

    @Override
    public ToWgs84 build() {
        return new ToWgs84(first(), last(), index(),
                token(2), token(4), token(6), token(8), token(10), token(12), token(14));
    }
}
