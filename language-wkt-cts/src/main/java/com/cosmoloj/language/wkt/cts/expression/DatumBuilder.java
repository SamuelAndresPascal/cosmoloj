package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintBeforeIndexPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
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
public class DatumBuilder extends CheckTokenBuilder<Token, Datum>
        implements PredicateListTokenBuilder<Token>, ConstraintBeforeIndexPredicateTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktName.DATUM,
                LeftDelimiter.class::isInstance,
                QuotedName.class::isInstance,
                SpecialSymbol.COMMA,
                Spheroid.class::isInstance,
                SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance),
                Predicates.of(Authority.class::isInstance).or(ToWgs84.class::isInstance),
                SpecialSymbol.COMMA.or(RightDelimiter.class::isInstance),
                Authority.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public Predicate<? super Token> constraintBeforeIndex(final int before, final int index) {
        return switch (before) {
            case 1 -> switch (index) {
                case 6, 8 -> SpecialSymbol.COMMA;
                default -> Predicates.yes();
            };
            case 2 -> index == 8 ? ToWgs84.class::isInstance : Predicates.yes();
            default -> Predicates.yes();
        };
    }

    @Override
    public Datum build() {
        return new Datum(first(), last(), index(), token(2), token(4));
    }
}
