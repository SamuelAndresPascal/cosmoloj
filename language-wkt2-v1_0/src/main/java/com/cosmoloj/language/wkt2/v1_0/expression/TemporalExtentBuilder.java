package com.cosmoloj.language.wkt2.v1_0.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v1_0.lexeme.compound.Datetime;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v1_0.lexeme.simple.WktKeyword;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author Samuel Andrés
 */
public class TemporalExtentBuilder extends CheckTokenBuilder<Lexeme, TemporalExtent>
        implements PredicateListTokenBuilder<Lexeme> {

    @Override
    public List<Predicate<? super Lexeme>> predicates() {
        return List.of(WktKeyword.TIMEEXTENT,
                LeftDelimiter.class::isInstance,
                Datetime.INSTANCE_OF.or(QuotedLatinText.class::isInstance),
                SpecialSymbol.COMMA,
                Datetime.INSTANCE_OF.or(QuotedLatinText.class::isInstance),
                RightDelimiter.class::isInstance);
    }

    @Override
    public TemporalExtent build() {
        return new TemporalExtent(first(), last(), index(), (Lexeme) token(2), (Lexeme) token(4));
    }
}
