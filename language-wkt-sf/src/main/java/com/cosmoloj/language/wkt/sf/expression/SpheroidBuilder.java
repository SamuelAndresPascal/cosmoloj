package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import com.cosmoloj.language.wkt.sf.lexeme.WktName;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class SpheroidBuilder extends CheckTokenBuilder<Token, Spheroid>
        implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(
                WktName.SPHEROID.or(WktName.ELLIPSOID),
                LeftDelimiter.class::isInstance,
                QuotedName.class::isInstance,
                SpecialSymbol.COMMA,
                SignedNumericLiteral.class::isInstance,
                SpecialSymbol.COMMA,
                SignedNumericLiteral.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public Spheroid build() {
        return new Spheroid(first(), last(), index(), token(0), token(2), token(4), token(6));
    }
}
