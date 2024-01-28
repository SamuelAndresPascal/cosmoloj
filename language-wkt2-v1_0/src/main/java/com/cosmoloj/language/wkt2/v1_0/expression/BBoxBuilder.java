package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class BBoxBuilder extends CheckTokenBuilder<Token, BBox> implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktKeyword.BBOX,
                LeftDelimiter.class::isInstance,
                SignedNumericLiteral.class::isInstance,
                SpecialSymbol.COMMA,
                SignedNumericLiteral.class::isInstance,
                SpecialSymbol.COMMA,
                SignedNumericLiteral.class::isInstance,
                SpecialSymbol.COMMA,
                SignedNumericLiteral.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public BBox build() {
        return new BBox(first(), last(), index(), token(2), token(4), token(6), token(8));
    }
}
