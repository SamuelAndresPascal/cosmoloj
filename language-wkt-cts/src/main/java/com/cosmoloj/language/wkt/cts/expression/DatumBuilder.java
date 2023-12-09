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
                QuotedName.QUOTED_NAME,
                SpecialSymbol.COMMA,
                Spheroid.INSTANCE_OF,
                SpecialSymbol.COMMA.or(RightDelimiter.INSTANCE_OF),
                Authority.INSTANCE_OF.or(ToWgs84.INSTANCE_OF),
                SpecialSymbol.COMMA.or(RightDelimiter.INSTANCE_OF),
                Authority.INSTANCE_OF,
                RightDelimiter.INSTANCE_OF);
    }

    @Override
    public Predicate<? super Token> constraintBeforeIndex(final int before, final int index) {
        return switch (before) {
            case 1 -> switch (index) {
                case 6, 8 -> SpecialSymbol.COMMA;
                default -> t -> true;
            };
            case 2 -> index == 8 ? ToWgs84.INSTANCE_OF : t -> true;
            default -> t -> true;
        };
    }

    @Override
    public Datum build() {
        return new Datum(first(), last(), index(), token(2), token(4));
    }
}
