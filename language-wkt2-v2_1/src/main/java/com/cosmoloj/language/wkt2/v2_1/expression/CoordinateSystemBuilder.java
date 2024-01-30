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
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class CoordinateSystemBuilder extends CheckTokenBuilder<Token, CoordinateSystem>
        implements PredicateIndexTokenBuilder<Token> {

    private static final int NOT_CLOSED = -1;
    private int rightDelimiterIndex = NOT_CLOSED;
    private boolean includeCs = false; // WKT-CTS compatibility

    protected boolean isOpen() {
        return rightDelimiterIndex != NOT_CLOSED;
    }

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {

        if (currentIndex == 0) {
            return WktKeyword.CS.or(pb(Axis.class, Unit.class)); // WKT-CTS compatibility
        }

        if (includeCs) {

            return switch (currentIndex) {
                case 1 -> LeftDelimiter.class::isInstance;
                case 2 -> Predicates.in(CsType.class);
                case 3 -> SpecialSymbol.COMMA;
                case 4 -> UnsignedInteger.class::isInstance;
                default -> {
                    if (isOpen()) {
                        yield odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA) : pb(Identifier.class);
                    } else {
                        yield odd() ? pb(Axis.class, Unit.class) : SpecialSymbol.COMMA;
                    }
                }
            };

        } else {
            // WKT CTS compatibility
            return switch (currentIndex) {
                case 1 -> SpecialSymbol.COMMA;
                case 2 -> pb(Axis.class, Unit.class);
                case 3 -> SpecialSymbol.COMMA;
                default -> {
                    if (size() == 8) {
                        yield Predicates.no();
                    }
                    yield odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA) : pb(Axis.class, Unit.class);
                }
            };
        }
    }

    private CsType.Type type() {
        if (size() > 2) {
            return ((EnumLexeme<CsType>) token(2)).getSemantics().getType();
        }
        throw new IllegalStateException();
    }

    protected boolean wktCts(final Object token) {
        return !includeCs;
    }

    @Override
    protected void afterAdd(final Token token) {
        if (size() == 1 && WktKeyword.CS.test(token)) {
            includeCs = true;
        }
        if (isOpen() && token instanceof RightDelimiter) {
            rightDelimiterIndex = size() - 1;
        }
    }

    @Override
    public CoordinateSystem build() {
        final List<Token> type = tokens(Predicates.in(CsType.class));
        final List<Token> dimention = tokens(UnsignedInteger.class::isInstance);

        return new CoordinateSystem(first(), last(), index(),
                type.isEmpty() ? null : (EnumLexeme<CsType>) type.get(0),
                dimention.isEmpty() ? null : (UnsignedInteger) dimention.get(0),
                tokens(Identifier.class::isInstance),
                tokens(Axis.class::isInstance),
                firstToken(Unit.class::isInstance));
    }

    public static class Ellipsoidal2DCoordinateSystemBuilder extends CoordinateSystemBuilder {

        @Override
        public Predicate<? super Token> predicate(final int currentIndex) {
            return switch (currentIndex) {
                case 0 -> WktKeyword.CS.or(pb(Axis.class, Unit.class)); // WKT-CTS compatibility
                case 1 -> SpecialSymbol.COMMA.and(this::wktCts).or(LeftDelimiter.class::isInstance);
                case 2 -> CsType.ELLIPSOIDAL.or(pb(Axis.class, Unit.class).and(this::wktCts));
                case 3 -> SpecialSymbol.COMMA;
                case 4 -> pb(UnsignedInteger.class)
                        .and(t -> ((UnsignedInteger) t).getSemantics().equals(2))
                        .or(pb(Axis.class, Unit.class).and(this::wktCts));
                default -> {
                    if (odd() && isOpen()) {
                        yield pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
                    } else if (even() && isOpen()) {
                        yield pb(Identifier.class).or(pb(Axis.class, Unit.class).and(this::wktCts));
                    } else if (odd()) {
                        yield SpecialSymbol.COMMA;
                    } else {
                        yield pb(Axis.class, Unit.class);
                    }
                }
            };
        }

        @Override
        public CoordinateSystem.Ellipsoidal2DCoordinateSystem build() {
            final List<Token> type = tokens(Predicates.in(CsType.class));
            final List<Token> dimention = tokens(UnsignedInteger.class::isInstance);

            return new CoordinateSystem.Ellipsoidal2DCoordinateSystem(first(), last(), index(),
                    type.isEmpty() ? null : (EnumLexeme<CsType>) type.get(0),
                    dimention.isEmpty() ? null : (UnsignedInteger) dimention.get(0),
                    tokens(Identifier.class::isInstance),
                    tokens(Axis.class::isInstance),
                    firstToken(Unit.class::isInstance));
        }
    }
}
