package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforePredicateTokenBuilder;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.expression.Parameter;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.util.function.Predicates;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ProjectedCsBuilder extends CheckTokenBuilder<Token, ProjectedCs>
        implements ConstraintBeforePredicateTokenBuilder<Token> {

    @Override
    public Predicate<? super Token> predicate() {
        return switch (size()) {
            case 0 -> WktName.PROJCS;
            case 1 -> LeftDelimiter.class::isInstance;
            case 2 -> QuotedName.class::isInstance;
            case 3, 5, 7 -> SpecialSymbol.COMMA;
            case 4 -> GeographicCs.class::isInstance;
            case 6 -> Projection.class::isInstance;
            default -> switch (parity()) {
                case EVEN -> {
                    if (beyond(9)) {
                        yield pb(Axis.class, Authority.class, Parameter.class, Unit.class);
                    } else if (beyond(7)) {
                        yield pb(Parameter.class, Unit.class);
                    } else {
                        yield Predicates.no();
                    }
                }
                case ODD -> beyond(8) ? SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance) : Predicates.no();
                default -> Predicates.no();
            };
        };
    }

    @Override
    public Predicate<? super Token> constraintBefore(final int before) {

        if (odd()) {
            return Predicates.yes();
        }

        return switch (before) {
            case 1 -> beyond(7) ? SpecialSymbol.COMMA : Predicates.yes();
            case 2 -> {
                if (beyond(9) && waiting(pb(Axis.class, Authority.class))) {
                    yield pb(Unit.class, Axis.class);
                } else if (beyond(7) && waiting(pb(Parameter.class, Unit.class))) {
                    yield pb(Parameter.class, Projection.class);
                } else {
                    yield Predicates.yes();
                }
            }
            case 4 -> {
                if (beyond(9) && previous(2, Axis.class::isInstance)) {
                    if (waiting(Axis.class)) {
                        yield pb(Axis.class).negate();
                    } else if (waiting(Authority.class)) {
                        yield Axis.class::isInstance;
                    } else {
                        yield Predicates.yes();
                    }
                } else {
                    yield Predicates.yes();
                }
            }
            default -> Predicates.yes();
        };
    }

    @Override
    public ProjectedCs build() {

        final List<Axis> axis = tokens(Axis.class);
        final List<Unit> units = tokens(Unit.class);

        final Axis axis1;
        final Axis axis2;
        if (!axis.isEmpty()) {
            axis1 = axis.get(0);
            axis2 = axis.get(1);
        } else {
            axis1 = null;
            axis2 = null;
        }

        return new ProjectedCs(first(), last(), index(),
                token(2),
                token(4),
                token(6),
                tokens(Parameter.class),
                !units.isEmpty() ? units.get(0) : null,
                axis1, axis2,
                testToken(size() - 2, Authority.class) ? token(size() - 2) : null);
    }
}
