package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.ConstraintLastPredicateTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
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
public class SpheroidBuilder extends CheckTokenBuilder<Token, Spheroid>
        implements PredicateListTokenBuilder<Token>, ConstraintLastPredicateTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktName.SPHEROID.or(WktName.ELLIPSOID),
                LeftDelimiter.class::isInstance,
                QuotedName.QUOTED_NAME,
                SpecialSymbol.COMMA,
                SignedNumericLiteral.INSTANCE_OF,
                SpecialSymbol.COMMA,
                SignedNumericLiteral.INSTANCE_OF,
                SpecialSymbol.COMMA.or(RightDelimiter.INSTANCE_OF),
                Authority.INSTANCE_OF,
                RightDelimiter.INSTANCE_OF);
    }

    @Override
    public Predicate<? super Token> constraintLast(final int index) {
        return index == 8 ? SpecialSymbol.COMMA : t -> true;
    }

    @Override
    public Spheroid build() {
        return new Spheroid(first(), last(), index(), token(0), token(2), token(4), token(6), token(8));
    }
}
