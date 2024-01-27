package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class MeridianBuilder extends CheckTokenBuilder<Token, Meridian> implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktKeyword.MERIDIAN,
                LeftDelimiter.class::isInstance,
                SignedNumericLiteral.class::isInstance,
                SpecialSymbol.COMMA,
                Unit.Angle.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public Meridian build() {
        return new Meridian(first(), last(), index(), token(2), token(4));
    }
}
