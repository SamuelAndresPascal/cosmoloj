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
            case 4 -> Datum.INSTANCE_OF;
            case 6 -> PrimeMeridian.INSTANCE_OF_CTS;
            case 8 -> Unit.INSTANCE_OF_CTS;
            case 10 -> Authority.INSTANCE_OF.or(Axis.INSTANCE_OF);
            case 12, 14 -> Axis.INSTANCE_OF;
            case 16 -> Authority.class::isInstance;
            default -> {
                yield (odd() && beyond(8) && below(18))
                    ? SpecialSymbol.COMMA.or(RightDelimiter.INSTANCE_OF)
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
                        if (below(16) && current(SpecialSymbol.COMMA)) {
                            yield Axis.INSTANCE_OF.or(Unit.INSTANCE_OF_CTS);
                        } else if (below(18) && current(RightDelimiter.INSTANCE_OF)) {
                            // un délimiteur de fermeture doit suivre une authorité
                            yield Authority.INSTANCE_OF.or(Axis.INSTANCE_OF.or(Unit.INSTANCE_OF_CTS));
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
        final boolean hasAxis = size() > 10 && testToken(10, Axis.INSTANCE_OF);
        final boolean hasAuthority = (size() > 16 && testToken(16, Authority.INSTANCE_OF))
                || (size() > 10 && testToken(10, Authority.INSTANCE_OF));
        return new GeocentricCs(first(), last(), index(), token(2), token(4), token(6), token(8),
                hasAxis ? token(10) : null,
                hasAxis ? token(12) : null,
                hasAxis ? token(14) : null,
                (hasAuthority ? (size() == 18 ? token(16) : token(10)) : null));
    }
}
