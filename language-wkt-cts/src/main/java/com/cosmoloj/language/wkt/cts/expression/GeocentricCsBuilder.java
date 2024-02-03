package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.util.function.Predicates;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class GeocentricCsBuilder extends CheckTokenBuilder<Token, GeocentricCs>
        implements ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate() {
        return switch (size()) {
            case 0 -> WktName.GEOCCS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedName.class::isInstance;
            case 3, 5, 7 -> SpecialSymbol.COMMA;
            case 4 -> Datum.class::isInstance;
            case 6 -> PrimeMeridian.class::isInstance;
            case 8 -> Unit.class::isInstance;
            case 10 -> pb(Authority.class, Axis.class);
            case 12, 14 -> Axis.class::isInstance;
            case 16 -> Authority.class::isInstance;
            default -> {
                yield (odd() && beyond(8) && below(18))
                    ? SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance)
                    : Predicates.no();
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        return switch (index) {
                case 10, 12, 14, 16 -> SpecialSymbol.COMMA;
                default -> {
                    if (odd() && beyond(8)) {
                        if (below(16) && waiting(SpecialSymbol.COMMA)) {
                            yield pb(Axis.class, Unit.class);
                        } else if (below(18) && waiting(RightDelimiter.class)) {
                            // un délimiteur de fermeture doit suivre une authorité
                            yield pb(Authority.class, Axis.class, Unit.class);
                        } else {
                            yield Predicates.no();
                        }
                    }
                    yield Predicates.yes();
                }
            };
    }

    @Override
    public GeocentricCs build() {
        final boolean hasAxis = size() > 10 && testToken(10, Axis.class);
        final boolean hasAuthority = (size() > 16 && testToken(16, Authority.class))
                || (size() > 10 && testToken(10, Authority.class));
        return new GeocentricCs(first(), last(), index(),
                token(2),
                token(4),
                token(6),
                token(8),
                hasAxis ? token(10) : null,
                hasAxis ? token(12) : null,
                hasAxis ? token(14) : null,
                (hasAuthority ? (size() == 18 ? token(16) : token(10)) : null));
    }
}
