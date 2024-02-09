package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.AxisNameAbrev;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import com.cosmoloj.util.function.Predicates;
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
            case 6 -> pb(AxisOrder.class, AxisRange.class, Unit.class, Identifier.class);
            case 8 -> pb(AxisRange.class, Unit.class, Identifier.class);
            case 10 -> pb(Identifier.class, Unit.class);
            default -> {
                if (odd() && beyond(4)) {
                    yield pb(RightDelimiter.class).or(SpecialSymbol.COMMA);
                } else if (even() && beyond(10)) {
                    yield pb(Identifier.class);
                }
                yield Predicates.no();
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int currentIndex) {
        return (even() && beyond(5)) ? SpecialSymbol.COMMA : Predicates.yes();
    }

    @Override
    public Axis build() {
        return new Axis(first(), last(), index(),
                token(2),
                token(4),
                firstToken(AxisOrder.class),
                firstToken(AxisRange.class),
                firstToken(Unit.class),
                tokens(Identifier.class));
    }
}
