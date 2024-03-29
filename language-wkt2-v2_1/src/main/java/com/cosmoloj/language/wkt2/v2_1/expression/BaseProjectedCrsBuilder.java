package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class BaseProjectedCrsBuilder extends CheckTokenBuilder<Token, BaseProjectedCrs>
        implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktKeyword.BASEPROJCRS,
                LeftDelimiter.class::isInstance,
                QuotedLatinText.class::isInstance,
                SpecialSymbol.COMMA,
                BaseGeodeticCrs.class::isInstance,
                SpecialSymbol.COMMA,
                Operation.MapProjection.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public BaseProjectedCrs build() {
        return new BaseProjectedCrs(first(), last(), index(), token(2), token(4), token(6));
    }
}
