package com.cosmoloj.language.wkt2.v2_1.expression;

import com.cosmoloj.language.api.semantic.Lexeme;
import com.cosmoloj.language.common.impl.builder.CheckTokenBuilder;
import com.cosmoloj.language.common.impl.builder.PredicateListTokenBuilder;
import com.cosmoloj.language.wkt.sf.lexeme.LeftDelimiter;
import com.cosmoloj.language.wkt.sf.lexeme.RightDelimiter;
import com.cosmoloj.language.wkt2.v2_1.lexeme.compound.Datetime;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.QuotedLatinText;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.SpecialSymbol;
import com.cosmoloj.language.wkt2.v2_1.lexeme.simple.WktKeyword;
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
                pb(Datetime.class, QuotedLatinText.class),
                SpecialSymbol.COMMA,
                pb(Datetime.class, QuotedLatinText.class),
                RightDelimiter.class::isInstance);
    }

    @Override
    public TemporalExtent build() {
        return new TemporalExtent(first(), last(), index(), (Lexeme) token(2), (Lexeme) token(4));
    }
}
