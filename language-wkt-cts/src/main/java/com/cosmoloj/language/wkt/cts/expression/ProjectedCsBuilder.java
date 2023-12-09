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
            case 2 -> QuotedName.QUOTED_NAME;
            case 3, 5, 7 -> SpecialSymbol.COMMA;
            case 4 -> GeographicCs.class::isInstance;
            case 6 -> Projection.INSTANCE_OF_CTS;
            default -> switch (parity()) {
                case EVEN -> {
                    if (beyond(9)) {
                        yield Axis.INSTANCE_OF.or(Authority.INSTANCE_OF)
                                .or(Parameter.INSTANCE_OF).or(Unit.INSTANCE_OF_CTS);
                    } else if (beyond(7)) {
                        yield Parameter.INSTANCE_OF.or(Unit.INSTANCE_OF_CTS);
                    } else {
                        yield t -> false;
                    }
                }
                case ODD -> {
                    if (beyond(8)) {
                        yield SpecialSymbol.COMMA.or(RightDelimiter.INSTANCE_OF);
                    } else {
                        yield t -> false;
                    }
                }
                default -> {
                    yield t -> false;
                }
            };
        };
    }

    @Override
    public Predicate<? super Token> constraintBefore(final int before) {

        if (odd()) {
            return t -> true;
        }

        return switch (before) {
            case 1 -> beyond(7) ? SpecialSymbol.COMMA : t -> true;
            case 2 -> {
                if (beyond(9) && current(Axis.INSTANCE_OF.or(Authority.INSTANCE_OF))) {
                    yield Unit.INSTANCE_OF_CTS.or(Axis.INSTANCE_OF);
                } else if (beyond(7) && current(Parameter.INSTANCE_OF.or(Unit.INSTANCE_OF_CTS))) {
                    yield Parameter.INSTANCE_OF.or(Projection.INSTANCE_OF_CTS);
                } else {
                    yield t -> true;
                }
            }
            case 4 -> {
                if (beyond(9) && previous(2, Axis.INSTANCE_OF)) {
                    if (current(Axis.INSTANCE_OF)) {
                        yield Axis.INSTANCE_OF.negate();
                    } else if (current(Authority.INSTANCE_OF)) {
                        yield Axis.INSTANCE_OF;
                    } else {
                        yield t -> true;
                    }
                } else {
                    yield t -> true;
                }
            }
            default -> t -> true;
        };
    }

    @Override
    public ProjectedCs build() {

        final List<Axis> axis = tokens(Axis.INSTANCE_OF);
        final List<Unit> units = tokens(Unit.INSTANCE_OF_CTS);

        final Axis axis1;
        final Axis axis2;
        if (!axis.isEmpty()) {
            axis1 = axis.get(0);
            axis2 = axis.get(1);
        } else {
            axis1 = null;
            axis2 = null;
        }

        return new ProjectedCs(first(), last(), index(), token(2), token(4),
                token(6), tokens(Parameter.INSTANCE_OF),
                !units.isEmpty() ? units.get(0) : null,
                axis1, axis2,
                testToken(size() - 2, Authority.INSTANCE_OF) ? token(size() - 2) : null);
    }
}
