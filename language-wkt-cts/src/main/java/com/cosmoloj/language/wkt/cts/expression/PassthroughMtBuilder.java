package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.common.number.lexeme.compound.SignedNumericLiteral;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.SpecialSymbol;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class PassthroughMtBuilder extends CheckTokenBuilder<Token, PassthroughMt>
        implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktName.PASSTHROUGH_MT,
                LeftDelimiter.class::isInstance,
                SignedNumericLiteral.INSTANCE_OF,
                SpecialSymbol.COMMA,
                MathTransform.INSTANCE_OF,
                RightDelimiter.INSTANCE_OF);
    }

    @Override
    public PassthroughMt build() {
        return new PassthroughMt(first(), last(), index(), token(2), token(4));
    }
}
