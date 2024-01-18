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
            case 4 -> Ellipsoid.INSTANCE_OF;
            default -> {
                if (odd() && rightDelimiterIndex == NOT_CLOSED) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                } else if (even() && rightDelimiterIndex == NOT_CLOSED) {
                    yield Anchor.INSTANCE_OF.or(Identifier.class::isInstance);
                } else if (odd()) {
                    yield PrimeMeridian.INSTANCE_OF;
                } else {
                    yield SpecialSymbol.COMMA;
                }
            }
        };
    }

    @Override
    public void add(final Token token) {
        super.add(token);
        if (rightDelimiterIndex == NOT_CLOSED && RightDelimiter.class.isInstance(token)) {
            rightDelimiterIndex = size() - 1;
        }
    }

    @Override
    public GeodeticDatum build() {

        return new GeodeticDatum(first(), last(), index(), token(2), token(4),
                firstToken(Anchor.INSTANCE_OF),
                tokens(Identifier.class::isInstance),
                firstToken(PrimeMeridian.INSTANCE_OF));
    }
}
