package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.CsType;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import com.cosmoloj.util.function.Predicates;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class OrdinalDateTimeCoordinateSystemBuilder extends CheckTokenBuilder<Token, OrdinalDateTimeCoordinateSystem>
        implements PredicateIndexTokenBuilder<Token> {

    private static final int NOT_CLOSED = -1;

    private int rightDelimiterIndex = NOT_CLOSED;
    private int axisCount = 0;


    protected boolean isOpen() {
        return rightDelimiterIndex != NOT_CLOSED;
    }

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {

        return switch (currentIndex) {
            case 0 -> WktKeyword.CS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> Predicates.in(CsType.class);
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> UnsignedInteger.class::isInstance;
            default -> {
                if (isOpen()) {
                    yield odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA) : pb(Identifier.class);
                } else {
                    yield odd() ? pb(OrdinalDateTimeAxis.class) : SpecialSymbol.COMMA;
                }
            }
        };
    }

    @Override
    protected void afterAdd(final Token token) {
        if (isOpen() && token instanceof RightDelimiter) {
            rightDelimiterIndex = size() - 1;
        }
        if (token instanceof OrdinalDateTimeAxis) {
            axisCount++;
        }
    }

    @Override
    public OrdinalDateTimeCoordinateSystem build() {
        final EnumLexeme<CsType> type = firstToken(Predicates.in(CsType.class));
        final UnsignedInteger dimention = firstToken(UnsignedInteger.class);

        return new OrdinalDateTimeCoordinateSystem(first(), last(), index(),
                type,
                dimention,
                tokens(Identifier.class),
                tokens(OrdinalDateTimeAxis.class));
    }
}
