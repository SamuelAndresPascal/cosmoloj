package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
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
            case 2 -> QuotedLatinText.QUOTED_LATIN_TEXT;
            case 3 -> SpecialSymbol.comma;
            case 4 -> Ellipsoid.INSTANCE_OF;
            default -> {
                if (odd() && rightDelimiterIndex == NOT_CLOSED) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.comma);
                } else if (even() && rightDelimiterIndex == NOT_CLOSED) {
                    yield Anchor.INSTANCE_OF.or(Identifier.INSTANCE_OF);
                } else if (odd()) {
                    yield PrimeMeridian.INSTANCE_OF;
                } else {
                    yield SpecialSymbol.comma;
                }
            }
        };
    }

    @Override
    public void add(final Token token) {
        super.add(token);
        if (rightDelimiterIndex == NOT_CLOSED && RightDelimiter.INSTANCE_OF.test(token)) {
            rightDelimiterIndex = size() - 1;
        }
    }

    @Override
    public GeodeticDatum build() {

        return new GeodeticDatum(first(), last(), index(), token(2), token(4),
                firstToken(Anchor.INSTANCE_OF),
                tokens(Identifier.INSTANCE_OF),
                firstToken(PrimeMeridian.INSTANCE_OF));
    }
}
