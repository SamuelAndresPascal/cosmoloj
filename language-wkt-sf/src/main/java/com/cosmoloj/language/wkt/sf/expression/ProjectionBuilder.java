package com.cosmoloj.language.wkt.sf.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.QuotedName;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.WktName;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class ProjectionBuilder extends CheckTokenBuilder<Token, Projection>
        implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktName.PROJECTION,
                LeftDelimiter.class::isInstance,
                QuotedName.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public Projection build() {
        return new Projection(first(), last(), index(), token(2));
    }
}
