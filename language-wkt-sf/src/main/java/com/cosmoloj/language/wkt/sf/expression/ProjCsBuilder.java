package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateIndexTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.language.wkt.sf.lexeme.WktName;
import com.cosmoloj.util.function.Predicates;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ProjCsBuilder extends CheckTokenBuilder<Token, ProjectedCs>
        implements PredicateIndexTokenBuilder<Token>, ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate(final int index) {
        return switch (index) {
            case 0 -> WktName.PROJCS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedName.class::isInstance;
            case 3, 5, 7 -> SpecialSymbol.COMMA;
            case 4 -> GeographicCs.class::isInstance;
            case 6 -> Projection.INSTANCE_OF;
            default -> {
                if (even() && beyond(7)) {
                    yield Parameter.INSTANCE_OF.or(Unit.INSTANCE_OF);
                } else if (odd() && beyond(8)) {
                    yield SpecialSymbol.COMMA.or(RightDelimiter.INSTANCE_OF);
                } else {
                    yield Predicates.no();
                }
            }
        };
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        if (even() && beyond(7)) {
            return SpecialSymbol.COMMA;
        } else if (odd() && beyond(8)) {
            if (current(SpecialSymbol.COMMA)) {
                return Parameter.INSTANCE_OF;
            } else if (current(RightDelimiter.INSTANCE_OF)) {
                return Unit.INSTANCE_OF;
            } else {
                return Predicates.no();
            }
        } else {
            return Predicates.yes();
        }
    }

    @Override
    public ProjectedCs build() {
        return new ProjectedCs(first(), last(), index(),
                token(2), token(4), token(6), tokens(Parameter.INSTANCE_OF), token(size() - 2));
    }
}
