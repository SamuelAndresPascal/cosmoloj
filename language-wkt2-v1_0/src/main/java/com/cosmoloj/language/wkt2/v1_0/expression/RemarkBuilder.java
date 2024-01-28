package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedUnicodeText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class RemarkBuilder extends CheckTokenBuilder<Token, Remark> implements PredicateListTokenBuilder<Token> {

    @Override
    public List<Predicate<? super Token>> predicates() {
        return List.of(WktKeyword.REMARK,
                LeftDelimiter.class::isInstance,
                QuotedUnicodeText.class::isInstance,
                RightDelimiter.class::isInstance);
    }

    @Override
    public Remark build() {
        return new Remark(first(), last(), index(), token(2));
    }
}
