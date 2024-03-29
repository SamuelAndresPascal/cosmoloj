package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
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
 */
public class GeodeticDatumBuilder extends CheckTokenBuilder<Token, GeodeticDatum>
        implements PredicateIndexTokenBuilder<Token> {

    private static final int NOT_CLOSED = -1;
    private int rightDelimiterIndex = NOT_CLOSED;

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.GEODETICDATUM.or(WktKeyword.DATUM);
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedLatinText.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> Ellipsoid.class::isInstance;
            default -> {
                if (odd() && rightDelimiterIndex == NOT_CLOSED) {
                    yield pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
                } else if (even() && rightDelimiterIndex == NOT_CLOSED) {
                    yield pb(Anchor.class, Identifier.class);
                } else if (odd()) {
                    yield PrimeMeridian.class::isInstance;
                } else {
                    yield SpecialSymbol.COMMA;
                }
            }
        };
    }

    @Override
    protected void afterAdd(final Token token) {
        if (rightDelimiterIndex == NOT_CLOSED && token instanceof RightDelimiter) {
            rightDelimiterIndex = size() - 1;
        }
    }

    @Override
    public GeodeticDatum build() {

        return new GeodeticDatum(first(), last(), index(), token(2), token(4),
                firstToken(Anchor.class),
                tokens(Identifier.class),
                firstToken(PrimeMeridian.class));
    }
}
