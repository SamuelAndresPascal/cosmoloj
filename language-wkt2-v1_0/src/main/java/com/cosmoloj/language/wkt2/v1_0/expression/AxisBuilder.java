package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.AxisNameAbrev;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class AxisBuilder extends CheckTokenBuilder<Token, Axis> implements PredicateIndexTokenBuilder<Token>,
        ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int currentIndex) {
        return switch (currentIndex) {
            case 0 -> WktKeyword.AXIS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> AxisNameAbrev.class::isInstance;
            case 3 -> SpecialSymbol.COMMA;
            case 4 -> AxisDirection.class::isInstance;
            case 6 -> AxisOrder.INSTANCE_OF.or(Unit.INSTANCE_OF).or(Identifier.INSTANCE_OF);
            case 8 -> Identifier.INSTANCE_OF.or(Unit.INSTANCE_OF);
            default -> {
                if (odd() && beyond(4)) {
                    yield RightDelimiter.INSTANCE_OF.or(SpecialSymbol.COMMA);
                } else if (even() && beyond(8)) {
                    yield Identifier.INSTANCE_OF;
                }
                yield t -> false;
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int currentIndex) {
        return (even() && beyond(5)) ? SpecialSymbol.COMMA : t -> true;
    }

    @Override
    public Axis build() {

        final AxisOrder order = (size() >= 8 && testToken(6, AxisOrder.INSTANCE_OF)) ?  token(6) : null;

        final Unit unit;
        if (size() >= 8 && testToken(6, Unit.INSTANCE_OF)) {
            unit = token(6);
        } else if (size() >= 10 && testToken(8, Unit.INSTANCE_OF)) {
            unit = token(8);
        } else {
            unit = null;
        }

        return new Axis(first(), last(), index(), token(2), token(4), order, unit, tokens(Identifier.INSTANCE_OF));
    }
}
