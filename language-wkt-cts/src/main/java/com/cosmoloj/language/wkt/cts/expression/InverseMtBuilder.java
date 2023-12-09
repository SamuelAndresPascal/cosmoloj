package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.cts.lexeme.WktName;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class InverseMtBuilder extends CheckTokenBuilder<Token, InverseMt> implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktName.INVERSE_MT,
                LeftDelimiter.class::isInstance,
                MathTransform.INSTANCE_OF,
                RightDelimiter.INSTANCE_OF);
    }

    @Override
    public InverseMt build() {
        return new InverseMt(first(), last(), index(), token(2));
    }
}
