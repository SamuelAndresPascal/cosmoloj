package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Token;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedUnicodeText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
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
                QuotedUnicodeText.INSTANCE_OF_QUOTED_UNICODE_TEXT,
                RightDelimiter.INSTANCE_OF);
    }

    @Override
    public Remark build() {
        return new Remark(first(), last(), index(), token(2));
    }
}
