package com.cosmoloj.language.wkt.cts.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
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
public class AuthorityBuilder extends CheckTokenBuilder<Token, Authority> implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktName.AUTHORITY,
                LeftDelimiter.class::isInstance,
                QuotedName.QUOTED_NAME,
                SpecialSymbol.COMMA,
                QuotedName.QUOTED_NAME,
                RightDelimiter.INSTANCE_OF);
    }

    @Override
    public Authority build() {
        return new Authority(first(), last(), index(), token(2), token(4));
    }
}
