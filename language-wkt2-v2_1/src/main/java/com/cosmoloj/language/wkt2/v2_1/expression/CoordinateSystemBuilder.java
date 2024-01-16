package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.simple.UnsignedInteger;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.CsType;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
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
            case 0 -> WktKeyword.CS.or(Axis.INSTANCE_OF.or(Unit.INSTANCE_OF)); // WKT-CTS compatibility
            case 1 -> SpecialSymbol.COMMA.and(this::wktCts).or(LeftDelimiter.class::isInstance);
            case 2 -> CsType.Lexeme.INSTANCE_OF.or(Axis.INSTANCE_OF.or(Unit.INSTANCE_OF).and(this::wktCts));
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> UnsignedInteger.UNSIGNED_INTEGER.or(Axis.INSTANCE_OF.or(Unit.INSTANCE_OF).and(this::wktCts));
            default -> {
                if (odd() && !isClosed()) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                } else if (even() && !isClosed()) {
                    yield Identifier.INSTANCE_OF.or(Axis.INSTANCE_OF.or(Unit.INSTANCE_OF).and(this::wktCts));
                } else if (odd()) {
                    yield SpecialSymbol.COMMA;
                } else {
                    yield Axis.INSTANCE_OF.or(Unit.INSTANCE_OF);
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
        if (!isClosed() && RightDelimiter.INSTANCE_OF.test(token)) {
            rightDelimiterIndex = size() - 1;
        }
    }

    @Override
    public CoordinateSystem build() {
        final List<Token> type = tokens(CsType.Lexeme.INSTANCE_OF);
        final List<Token> dimention = tokens(UnsignedInteger.UNSIGNED_INTEGER);

        return new CoordinateSystem(first(), last(), index(),
                type.isEmpty() ? null : (CsType.Lexeme) type.get(0),
                dimention.isEmpty() ? null : (UnsignedInteger) dimention.get(0),
                tokens(Identifier.INSTANCE_OF),
                tokens(Axis.INSTANCE_OF),
                firstToken(Unit.INSTANCE_OF));
    }

    public static class Ellipsoidal2DCoordinateSystemBuilder extends CoordinateSystemBuilder {

        @Override
        public Predicate<? super Token> predicate(final int currentIndex) {
            return switch (currentIndex) {
                case 0 -> WktKeyword.CS.or(Axis.INSTANCE_OF.or(Unit.INSTANCE_OF)); // WKT-CTS compatibility
                case 1 -> SpecialSymbol.COMMA.and(this::wktCts).or(LeftDelimiter.class::isInstance);
                case 2 -> CsType.ellipsoidal.or(Axis.INSTANCE_OF.or(Unit.INSTANCE_OF).and(this::wktCts));
                case 3 -> SpecialSymbol.COMMA;
                case 4 -> UnsignedInteger.UNSIGNED_INTEGER.and(t -> ((UnsignedInteger) t).getSemantics().equals(2))
                        .or(Axis.INSTANCE_OF.or(Unit.INSTANCE_OF).and(this::wktCts));
                default -> {
                    if (odd() && !isClosed()) {
                        yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                    } else if (even() && !isClosed()) {
                        yield Identifier.INSTANCE_OF.or(Axis.INSTANCE_OF.or(Unit.INSTANCE_OF).and(this::wktCts));
                    } else if (odd()) {
                        yield SpecialSymbol.COMMA;
                    } else {
                        yield Axis.INSTANCE_OF.or(Unit.INSTANCE_OF);
                    }
                }
            };
        }

        @Override
        public CoordinateSystem.Ellipsoidal2DCoordinateSystem build() {
            final List<Token> type = tokens(CsType.Lexeme.INSTANCE_OF);
            final List<Token> dimention = tokens(UnsignedInteger.UNSIGNED_INTEGER);

            return new CoordinateSystem.Ellipsoidal2DCoordinateSystem(first(), last(), index(),
                    type.isEmpty() ? null : (CsType.Lexeme) type.get(0),
                    dimention.isEmpty() ? null : (UnsignedInteger) dimention.get(0),
                    tokens(Identifier.INSTANCE_OF), tokens(Axis.INSTANCE_OF),
                    firstToken(Unit.INSTANCE_OF));
        }
    }
}
