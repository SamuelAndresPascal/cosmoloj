package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.impl.semantic.EnumLexeme;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.CsType;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
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

    protected boolean isClosed() {
        return rightDelimiterIndex != NOT_CLOSED;
    }

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.CS.or(pb(Axis.class, Unit.class)); // WKT-CTS compatibility
            case 1 -> SpecialSymbol.COMMA.and(this::wktCts).or(LeftDelimiter.class::isInstance);
            case 2 -> Predicates.in(CsType.class).or(pb(Axis.class, Unit.class).and(this::wktCts));
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> pb(UnsignedInteger.class).or(pb(Axis.class, Unit.class).and(this::wktCts));
            default -> {
                if (odd() && !isClosed()) {
                    yield pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
                } else if (even() && !isClosed()) {
                    yield pb(Identifier.class).or(pb(Axis.class, Unit.class).and(this::wktCts));
                } else if (odd()) {
                    yield SpecialSymbol.COMMA;
                } else {
                    yield pb(Axis.class, Unit.class);
                }
            }
        };
    }

    protected boolean wktCts(final Object token) {
        return !includeCs;
    }

    @Override
    public void add(final Token token) {
        super.add(token);
        if (size() == 1 && WktKeyword.CS.test(token)) {
            includeCs = true;
        }
        if (!isClosed() && token instanceof RightDelimiter) {
            rightDelimiterIndex = size() - 1;
        }
    }

    @Override
    public CoordinateSystem build() {
        final List<Token> type = tokens(Predicates.or(CsType.values()));
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
                    if (odd() && !isClosed()) {
                        yield pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
                    } else if (even() && !isClosed()) {
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
