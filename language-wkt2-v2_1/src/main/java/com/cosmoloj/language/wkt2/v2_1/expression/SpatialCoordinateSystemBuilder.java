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
public class SpatialCoordinateSystemBuilder extends CheckTokenBuilder<Token, SpatialCoordinateSystem>
        implements PredicateIndexTokenBuilder<Token> {

    private static final int NOT_CLOSED = -1;

    private int rightDelimiterIndex = NOT_CLOSED;
    private boolean includeCs = false; // WKT-CTS compatibility
    private int axisCount = 0;
    private boolean unitRead = false;


    protected boolean isOpen() {
        return rightDelimiterIndex != NOT_CLOSED;
    }

    protected boolean wktCts(final Object token) {
        return !includeCs;
    }

    private CsType.Type csType() {
        return ((EnumLexeme<CsType>) token(2)).getSemantics().getType();
    }

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {

        if (currentIndex == 0) {
            return WktKeyword.CS.or(pb(SpatialTemporalAxis.class, Unit.class)); // WKT-CTS compatibility
        }

        return includeCs ? wkt2Predicate(currentIndex) : wktCtsPredicate(currentIndex);
    }

    protected Predicate<? super Token> wkt2Predicate(final int currentIndex) {

        return switch (currentIndex) {
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> Predicates.in(CsType.class);
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> UnsignedInteger.class::isInstance;
            default -> {
                if (isOpen()) {
                    yield odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA) : pb(Identifier.class);
                } else {
                    if (null == csType()) {
                        yield odd() ? pb(SpatialTemporalAxis.class) : SpecialSymbol.COMMA;
                    } else {
                        yield switch (csType()) {
                            case SPATIAL -> odd() ? pb(SpatialTemporalAxis.class, Unit.class) : SpecialSymbol.COMMA;
                            case TEMPORAL, ORDINAL -> odd() ? pb(SpatialTemporalAxis.class) : SpecialSymbol.COMMA;
                        };
                    }
                }
            }
        };
    }

    protected Predicate<? super Token> wktCtsPredicate(final int currentIndex) {
        return switch (currentIndex) {
            case 1 -> SpecialSymbol.COMMA;
            case 2 -> pb(SpatialTemporalAxis.class, Unit.class);
            case 3 -> SpecialSymbol.COMMA;
            default -> {
                if (size() == 8) {
                    yield Predicates.no();
                }
                yield odd() ? SpecialSymbol.COMMA : pb(SpatialTemporalAxis.class, Unit.class);
            }
        };
    }

    @Override
    protected void afterAdd(final Token token) {
        if (size() == 1 && WktKeyword.CS.test(token)) {
            includeCs = true;
        }
        if (isOpen() && token instanceof RightDelimiter) {
            rightDelimiterIndex = size() - 1;
        }
        if (token instanceof SpatialTemporalAxis) {
            axisCount++;
        }
        if (token instanceof Unit) {
            unitRead = true;
        }
    }

    @Override
    public SpatialCoordinateSystem build() {
        final EnumLexeme<CsType> type = firstToken(Predicates.in(CsType.class));
        final UnsignedInteger dimention = firstToken(UnsignedInteger.class);

        return new SpatialCoordinateSystem(first(), last(), index(),
                type,
                dimention,
                tokens(Identifier.class),
                tokens(SpatialTemporalAxis.class),
                firstToken(Unit.class));
    }

    public static class Ellipsoidal2DCoordinateSystemBuilder extends SpatialCoordinateSystemBuilder {

        @Override
        protected Predicate<? super Token> wkt2Predicate(final int currentIndex) {

            return switch (currentIndex) {
                case 1 -> LeftDelimiter.class::isInstance;
                case 2 -> CsType.ELLIPSOIDAL;
                case 3 -> SpecialSymbol.COMMA;
                case 4 -> pb(UnsignedInteger.class)
                        .and(t -> ((UnsignedInteger) t).getSemantics().equals(2));
                default -> {
                    if (isOpen()) {
                        yield odd() ? pb(RightDelimiter.class).or(SpecialSymbol.COMMA) : pb(Identifier.class);
                    } else {
                        yield odd() ? pb(SpatialTemporalAxis.class, Unit.class) : SpecialSymbol.COMMA;
                    }
                }
            };
        }

        @Override
        protected Predicate<? super Token> wktCtsPredicate(final int currentIndex) {
            return switch (currentIndex) {
                case 1 -> SpecialSymbol.COMMA;
                case 2 -> pb(SpatialTemporalAxis.class, Unit.class);
                case 3 -> SpecialSymbol.COMMA;
                default -> {
                    if (size() == 6) {
                        yield Predicates.no();
                    }
                    yield odd() ? SpecialSymbol.COMMA : pb(SpatialTemporalAxis.class, Unit.class);
                }
            };
        }

        @Override
        public SpatialCoordinateSystem.Ellipsoidal2DCoordinateSystem build() {
            final EnumLexeme<CsType> type = firstToken(CsType.ELLIPSOIDAL);
            final UnsignedInteger dimention = firstToken(UnsignedInteger.class);

            return new SpatialCoordinateSystem.Ellipsoidal2DCoordinateSystem(first(), last(), index(),
                    type,
                    dimention,
                    tokens(Identifier.class),
                    tokens(SpatialTemporalAxis.class),
                    firstToken(Unit.class));
        }
    }
}
